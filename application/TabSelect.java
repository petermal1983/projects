package application;

import java.io.*;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javafx.util.Duration;
import javafx.application.Preloader;
import javafx.application.Preloader.StateChangeNotification.Type;
import javafx.animation.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/** @author
 *  Программа учета переводов текста всех компаниий, может хранить добавленные данные в видел таблицы
 *  Расчитывать какие компании сколько заказов обработали
 *  Хранит данные в формате CSV
 * **/

public class TabSelect extends Application {

    /**Область вывода сообщений о неправильности ввода*/
    private TextArea dgn = new TextArea();
    /**Кнопка добавления компании*/
    private Button addCompany;
    /**Кнопка удаления компании*/
    private Button delCompany;
    /**Кнопка принятия ввода данных в поля*/
    private Button buttonEnter;
    /**Поле отображающее названия компаний*/
    private ComboBox<String> comboBox1;
    /**Объект добавляющий эффект тени для кнопки*/
    private DropShadow effect = new DropShadow();
    private DropShadow shadow = new DropShadow();
    /**Объекты чтения из файлов данных*/
    private FileReader instream, inst;
    /**Объекты записи данных в файлы*/
    private static FileWriter outstream,out;
    /**Объекты хранения полученных данных из файлов*/
    private BufferedReader reader , rd;
    /**Объект хранения полученных данных из таблицы*/
    private BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream("Test.csv"),"Cp1251"));

    /**Контейнер для упорядочивания элементов первой вкладки в виде таблицы*/
    private GridPane root = new GridPane();
    /**Панель меню программы*/
    private MenuBar  menuBar = new MenuBar();
    /**Класс панели menuBar*/
    private Menu menuF = new Menu("Файлы");
    /**Классы-компоненты Menu с функциями сохранения, выхода из программы и открытия таблицы заказов соответственно*/
    private MenuItem itemSave = new MenuItem("Сохранить");
    private MenuItem itemExit = new MenuItem("Выход");
    private MenuItem itemOpen = new MenuItem("Открыть");
    /**Объект хранения списка подписей*/
    private ArrayList<Label> labelList;
    /**Объект хранения списка полей ввода*/
    private ArrayList<TextField> textFieldList;
    /**Объект хранения списка колонок таблицы*/
    private ArrayList<TableColumn> tableColumnList;
    /**Объект хранения списка названий компании*/
    private ArrayList<String> listCompany;
    /**Объект хранения списка объектов класса Order(Заказов)*/
    private List<Order> Items;
    /**Объект хранения словаря Название компании, Количество заказов*/
    private Map<String,Integer> countOrder=new HashMap<>();
    /**Панель с вкладками - основной элемент визуализации программы*/
    TabPane tabPane = new TabPane();
    /**Панель компоновки, которая располагает свои дочерние узлы в верхней, нижней, правой, левой и центральной частях панели*/
    BorderPane mainPane = new BorderPane();
    
    /**Вкладка добавление графика с колонками*/
    Tab barTab = new Tab();
    /**Вкладка первого экрана программы - поля ввода данных*/
    Tab tabA = new Tab();
    /**Вкладка второго экрана программы - таблица учета заказов*/
    Tab tabB = new Tab();
    /**Вкладка справочной информации по программе*/
    Tab tabD = new Tab();
    /**Вкладка окна вывода сообщений диагностики*/
    Tab tabDg = new Tab();

    /**Объявление экземпляра класса таблицы элементов*/
    TableView tv1 = new TableView();
    /**Панель компоновки дочерних узлов в таблицу из столбцов и строк ячеек с изменяемыми размерами*/
    GridPane pane = new GridPane();
    /**Массив подписей в Label*/
    String[] nameColum={"Отправитель", "Название", "Направление перевода", "Объем","Дата и время отправления","Дата и время получения","Ставка за ед", "Сумма"};
    /**Массив имен ассоциации в TableColumn*/
    String[] nameValues= {"sender","name","dir","volume","StringReceived","StringReceived1","price","total"};
    /**Массив подписей в TextField*/
    String[] nameTextField= {"Введите название отправителя","Введите название перевода","Введите направление","Введите объем","Введите дату начала","Введите дату конца","Введите ставку за страницу","Всего"};

    public TabSelect() throws IOException {
    	/**Массив списка названий компаний*/
        listCompany = new ArrayList<String>();
        /**Создание объекта чтения потока символов массива названия компаний*/
        instream = new FileReader("company.txt");
        /**Создание чтения потока символов массива порядка следования названий*/
        inst = new FileReader("CompanyOrder.txt");
        /**Создание буферного поток ввода символов который использует размер буфера по умолчанию для считывания по строкам для названий компаний*/
        reader = new BufferedReader(instream);
        /**Создание буферного поток ввода символов который использует размер буфера по умолчанию для считывания по строкам для порядка следования названий компаний*/
        rd= new BufferedReader(inst);
        /**Чтение слкдующей строки для названия компаний*/
        String ln = reader.readLine();
        /**Чтение слкдующей строки для порядка следования названия компаний*/
        String number = rd.readLine();
        /**Условие проверки на пустоту строки массива названий компаний*/
        while (ln != null)
        
        {
        	/**Добавление строки в массив названий компаний*/
            listCompany.add(ln);
            countOrder.put(ln,Integer.parseInt(number));
            /**Чтение слкдующей строки для названия компаний*/
            ln = reader.readLine();
            /**Чтение слкдующей строки для порядка следования названия компаний*/
            number=rd.readLine();
        }
        /**Закрытие потока считывания из файлов названий компаний*/
        instream.close();
        /**Закрытие потока считывания из файлов порядка следования названий компаний*/
        inst.close();
        /**Объявление массива данных колонок таблицы*/
        Items = new ArrayList<>();
        /**Создание буферного потока построкового считывания данных из файла хранения данных таблицы с указанием кодировки*/
        BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream("Test.csv"),"Cp1251"));
        /**Считывание слежующей строки*/
        String line=br.readLine();
        /**Условие проверки на пустоту строки*/
        while(line!=null) {
        	/**Вывод содержимого строки в консоль*/
        	System.out.println(line);
        	/**Указание разделителя элементов в массиве*/
        	String[] mass=line.split(";");
        	/**Добавление в массив элементов класса Order*/
        	Items.add(new Order(mass[0],mass[1],mass[2],Float.parseFloat(mass[3]),mass[4],mass[5],Integer.parseInt(mass[6]),(int)Float.parseFloat(mass[7])));
        	/**Чтение следующей строки*/
        	line=br.readLine();
        }
        /**Создание кнопки добавления компании*/
        addCompany = new Button("Добавить компанию");
        /**Задание стиля и шрифта кнопки*/
        addCompany.setStyle("-fx-font: 30 arial; -fx-base: #b6e7c9;");
        /**Создание кнопки внесения данных в таблицу*/
        buttonEnter = new Button("Принять");
        /**Задание расположения кнопки по ox*/
        buttonEnter.setLayoutX(100);
        /**Задание расположения кнопки по oy*/
        buttonEnter.setLayoutY(100);
        buttonEnter.setPrefSize(200, 80);
        buttonEnter.setStyle("-fx-font: 30 arial; -fx-base: #b6e7c9;");
        /**Создание кнопки удаления компан*/
        delCompany = new Button("Удалить компанию");
        delCompany.setStyle("-fx-font: 30 arial; -fx-base: #b6e7c9;");

        /**Создание выпадающего списка компаний*/
        comboBox1 = new ComboBox<String>();
        comboBox1.setStyle("-fx-font: 20 arial; -fx-base: #b6e7c9;");
        /**Задание количества отображаемых элементов выпадающего списка*/
        comboBox1.setVisibleRowCount(10);
        comboBox1.setEditable(true);
        /**Внесение в выпадающее меню элементов массива названий компаний*/
        comboBox1.getItems().addAll(listCompany);
        /**Внесение текста-подсказки в верхнюю строку выпадающего списка*/
        comboBox1.setPromptText(nameTextField[0]);

        /**Создание автоматически расширяемого массива наименований полей первой вкладки-экрана программы*/
        labelList=new ArrayList<Label>();
        /**Цикл с условием - по количеству элементов в массиве*/
        for(int i=0; i<10; i++) {
        	/**Добавление нового наименования поля при выполнении условия*/
            if(i<8)labelList.add(new Label(nameColum[i]+":"));
            /**Условие добавление наименования кнопки при достижении 9 элемента массива*/
            else if(i==8)labelList.add(new Label("Заполнить таблицу"));
            /**Условие добавления пустого наименования поля для 10 элемента*/
            else if(i==9)labelList.add(new Label(""));
            /**Задание шрифта наименованиям полей*/
            labelList.get(i).setFont(new Font("Arial", 25));
            /**Задание цвета заливки текста наименованиям полей*/
            labelList.get(i).setTextFill(Color.web("000000"));
            /**Задание фона наименованиям полей*/
            labelList.get(i).setBackground(new Background(new BackgroundFill(Color.DARKSEAGREEN, new CornerRadii(10), Insets.EMPTY)));
        }
        /**Создание автоматически расширяемого массива полей ввода данных первой вкладки-экрана программы*/
        textFieldList=new ArrayList<TextField>();
        /**Цикл с условием - по количеству элементов в массиве*/
        for(int i=0; i<7; i++) {
        	/**Добавление нового наименования поля при выполнении условия*/
        	textFieldList.add(new TextField());
        	/**Добавление в поле ввода текста-подсказки*/
        	textFieldList.get(i).setPromptText(nameTextField[i+1]);
        	/**Задание стилей полей ввода*/
        	textFieldList.get(i).setStyle(
                    "-fx-background-radius:10;-fx-border-radius:8;-fx-backgroundcolor:# ffefd5;-fx-border-width:3pt;-fx-border-color:#b6e7c9;-fxfontweight: bold;-fx-font-size:14pt; -fx-font-family:Georgia; -fx-fontstyle:italic");
            textFieldList.get(i).setPrefSize(600, 30);
            
        }
        /**Создание автоматически расширяемого массива колонок таблицы учета заказов второй вкладки-экрана программы*/
        tableColumnList=new ArrayList<TableColumn>();
        /**Цикл с условием - по количеству элементов в массиве*/
        for(int i =0; i<8; i++) {
        	/**Задание типа получаемых данных для 5 колонки*/
            if(i==4)	{tableColumnList.add(new TableColumn<Order,Float>(nameColum[i])); 		tableColumnList.get(i).setCellValueFactory(new PropertyValueFactory<Order, Float>(nameValues[i]));}
            /**Задание типа получаемых данных для колонок после 6-й*/
            else if(i>5) {tableColumnList.add(new TableColumn<Order,Integer>(nameColum[i]));	tableColumnList.get(i).setCellValueFactory(new PropertyValueFactory<Order, Integer>(nameValues[i]));}
            /**Задание типа получаемых данных для остальных колонок*/
            else {tableColumnList.add(new TableColumn<Order,String>(nameColum[i]));				tableColumnList.get(i).setCellValueFactory(new PropertyValueFactory<Order, String>(nameValues[i]));}
            tableColumnList.get(i).setPrefWidth(230);
            tableColumnList.get(i).setEditable(true);
            tableColumnList.get(i).setMinWidth(100);
            tabPane.setDisable(false);
        }

        root.setStyle("-fx-font: 30 arial; -fx-base: #b6e7c9;");
        /**Добавление меню в контейнер GridPane*/
        root.add(menuBar, 0, 0);

        effect.setOffsetX(1);
        effect.setOffsetY(1);

        /**Задание стилей элементов меню*/
        itemOpen.setStyle("-fx-text-fill:navy;-fx-font:bold 10pt Verdana;");
        /**Задание комбинаций клафиш для элементов меню*/
        itemOpen.setAccelerator(KeyCombination.keyCombination("Ctrl+O"));

        itemSave.setStyle("-fx-text-fill:navy;-fx-font:bold 10pt Verdana;");
        itemSave.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));

        itemExit.setStyle("-fx-text-fill:navy;-fx-font:bold 10pt Verdana;");
        itemExit.setAccelerator(KeyCombination.keyCombination("Ctrl+E"));

        /**Добавление элементов в меню*/
        menuF.getItems().addAll(itemOpen, itemSave, itemExit);

        menuBar.setEffect(effect);
        menuBar.setStyle("-fx-base:b6e7c9;-fx-border-width:0pt;-fx-border-color:navy;-fx-font:bold 15pt Verdana;");
        menuBar.setPrefSize(700, 20);
        menuBar.setBlendMode(BlendMode.HARD_LIGHT);
        menuBar.setCursor(Cursor.CLOSED_HAND);
        menuBar.getMenus().addAll(menuF);

        /**Задание названия графика*/
        barTab.setText("График поступления заказов");
        /**Задание стилей графика*/
        barTab.setStyle("-fx-font: 20 arial; -fx-base: #b6e7c9;");
        setBarTab(barTab);

        tv1.setEditable(true);
        tv1.setTableMenuButtonVisible(true);
        /**Получение названий колонок для таблицы из массива*/
        tv1.getColumns().addAll(tableColumnList);
        /**Получение содержимого строк таблицы из массива Items*/
        tv1.setItems(FXCollections.observableArrayList(Items));

        /**Задание фона-заливки бирюзовым цветом*/
        BackgroundFill myBF = new BackgroundFill(Color.AQUAMARINE, new
                CornerRadii(1),
                new Insets(0.0,0.0,0.0,0.0));
        pane.setBackground(new Background(myBF));
        pane.setLayoutX(20);
        pane.setLayoutY(20);
        pane.setCursor(Cursor.TEXT);
        pane.setStyle("-fx-font:bold 17pt Arial;-fx-text-fill:#a0522d;");
        pane.setVgap(10);
        pane.setGridLinesVisible(false);
        pane.setVgap(10);
        pane.addRow(1, labelList.get(0), comboBox1, addCompany, delCompany);
        //pane.addRow(i+2, label.get(i+1), text.get(i))
        /**Задание структуры расположения элементов контейнера GridPane*/
        pane.addRow(2, labelList.get(1), textFieldList.get(0));
        pane.addRow(3, labelList.get(2), textFieldList.get(1));
        pane.addRow(4, labelList.get(3), textFieldList.get(2));
        pane.addRow(5, labelList.get(4), textFieldList.get(3));
        pane.addRow(6, labelList.get(5), textFieldList.get(4));
        pane.addRow(7, labelList.get(6), textFieldList.get(5));
        pane.addRow(8, labelList.get(7), textFieldList.get(6));
        pane.addRow(9);
        pane.addRow(10);
        pane.addRow(11, labelList.get(8), buttonEnter, labelList.get(9));
        pane.addRow(12);
        pane.addRow(13);
        pane.addRow(14);
        pane.setHgap(50);
        pane.setVgap(40);

        /**Задание названия вкладки A*/
        tabA.setText("Данные заказа");
        /**Задание стиля названия вкладки A*/
        tabA.setStyle("-fx-font: 20 arial; -fx-base: #b6e7c9;");
        /**Задание содержимого вкладки A*/
        tabA.setContent(pane);

        /**Задание названия вкладки B*/
        tabB.setText("Таблица приема заказов");
        /**Задание стиля названия вкладки B*/
        tabB.setStyle("-fx-font: 20 arial; -fx-base: #b6e7c9;");
        /**Задание содержимого вкладки B*/
        tabB.setContent(tv1);

        /**Задание названия вкладки диагностики*/
        tabDg.setText("Диагностика");
        /**Задание стиля названия вкладки диагностики*/
        tabDg.setStyle("-fx-font: 20 arial; -fx-base: #b6e7c9;");
        /**Задание содержимого вкладки диагностики*/
        tabDg.setContent(dgn);

        /**Задание заливки панели с вкладками*/
        tabPane.setBackground(new Background(myBF));

        /**Задание названия вкладки Справка*/
        tabD.setText("Справка");
        /**Задание стиля названия вкладки Справка*/
        tabD.setStyle("-fx-font: 20 arial; -fx-base: #b6e7c9;");
        /**Создание компонента просмотра веб-страниц*/
        WebView browser = new WebView();
        /**Создание класса управление веб-страницей*/
        WebEngine webEngine = browser.getEngine();
        browser.setFontScale(2);
        /**Получение веб-страницы справки соотвествующим классом*/
        webEngine.load(getClass().getResource("index.html").toString());
        /**Задание содержимого вкладки Справка*/
        tabD.setContent(browser);

        tabPane.setTabClosingPolicy(TabClosingPolicy.ALL_TABS);
        tabA.setClosable(false);
        tabB.setClosable(false);
        barTab.setClosable(false);
        tabD.setClosable(false);
        tabDg.setClosable(false);

        /**Размещение вкладок на панели tabPane*/
        tabPane.getTabs().add(tabA);
        tabPane.getTabs().add(tabB);
        tabPane.getTabs().add(barTab);
        tabPane.getTabs().add(tabDg);
        tabPane.getTabs().add(tabD);
    }
    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Order management");
        Scene scene = new Scene(root, 800, 800, Color.WHITE);

        comboBox1.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (!comboBox1.getItems().contains(newValue)) {
                List<String> newItems = new ArrayList<>();
                newItems.addAll(comboBox1.getItems());
                comboBox1.getItems().setAll(newItems);
            }
        });
        /**Создание для кнопки ввода данных в таблицу эффекта отбрасывания тени по касанию курсора*/
        buttonEnter.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                buttonEnter.setEffect(shadow);
            }
        });
        /**Обработчик событий для внесения данных в таблицу учета заказов*/
        buttonEnter.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int pric = 0,tot = 0;
                float vol = 0;
                String sender, name, dir, volume, dat1, dat2, price, total;
                try { out = new FileWriter("companyOrder.txt",false); } catch (IOException e) { e.printStackTrace(); }
                sender = comboBox1.getValue();
                name = textFieldList.get(0).getText();
                dir = textFieldList.get(1).getText();
                volume = textFieldList.get(2).getText();
                dat1 =textFieldList.get(3).getText();
                dat2 = textFieldList.get(4).getText();
                price = textFieldList.get(5).getText();
                total = textFieldList.get(6).getText();
                SimpleDateFormat format = new SimpleDateFormat("EEE, MMM d, yyyy");
                SimpleDateFormat formatpar = new SimpleDateFormat("dd.MM.yyyy");
                Date tmpDate = new Date();
                try {tmpDate = formatpar.parse(dat1);} catch (ParseException e) {dgn.appendText("Неверный формат ввода даты получения\n");}
                Date tmpDate2 = new Date();
                try {tmpDate2 = formatpar.parse(dat2);} catch (ParseException e) {dgn.appendText("Неверный формат ввода даты отправки\n");}
                try {vol = Float.parseFloat(volume);} catch (Exception e) {dgn.appendText("Неверный формат ввода объема текста\n");}
                try {pric = Integer.parseInt(price);} catch (Exception e) {dgn.appendText("Неверный формат ввода стоимости\n");}
                try {tot = Integer.parseInt(total);} catch (Exception e) {dgn.appendText("Неверный формат ввода суммы\n");
                    labelList.get(9).setText("Внимание!,\n" + "Проверьте, пожалуйста,\n" + "правильность ввода данных!");}
                Order a = new Order(sender, name, dir, vol, format.format(tmpDate), format.format(tmpDate2), pric, tot);
                Items.add(a);
                countOrder.put(sender,countOrder.get(sender)+1);
                Collection<Integer> mass = countOrder.values();
                for (Integer i : mass)
                    try {out.write(String.valueOf(i)+"\r\n");} catch (IOException e) {e.printStackTrace();}
                try { out.close();} catch (IOException e) { e.printStackTrace(); }
                try { PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream("Test.csv"),"Cp1251"));
                    for(Order tmp: Items) {
                        System.out.println(tmp.toString());
                        pw.println(tmp.toString());
                    }
                    pw.close(); } catch (IOException e) { e.printStackTrace(); }
                setBarTab(barTab);
                tv1.setItems(FXCollections.observableArrayList(Items));
            }
        });

        addCompany.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                addCompany.setEffect(shadow);
            }
        });

        delCompany.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                delCompany.setEffect(shadow);
            }
        });

        addCompany.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                createWind(true, primaryStage);
            }
        });

        delCompany.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                createWind(false, primaryStage);
            }
        });

        itemSave.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            }
        });

        root.getChildren().addAll(pane);
        pane.setAlignment(Pos.CENTER);
        // root.getChildren().add(mainPane);
        root.add(mainPane, 0, 1);
        mainPane.setCenter(tabPane);
        mainPane.prefHeightProperty().bind(scene.heightProperty());
        mainPane.prefWidthProperty().bind(scene.widthProperty());
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setMaximized(true);
    }

    private void setBarTab(Tab barTab) {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String, Number> bc = new BarChart<String, Number>(xAxis, yAxis);
        bc.setTitle("График приема заказов");
        bc.setStyle("-fx-font: 30 arial; -fx-base: #b6e7c9;");
        xAxis.setLabel("Организация");
        xAxis.setStyle("-fx-font: 30 arial; -fx-base: #b6e7c9;");
        yAxis.setLabel("Количество");
        yAxis.setStyle("-fx-font: 30 arial; -fx-base: #b6e7c9;");
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Всего заказов");
        for(String company:listCompany)
            series1.getData().add(new XYChart.Data(company, countOrder.get(company)));
        XYChart.Series series2 = new XYChart.Series();
        bc.getData().addAll(series1);
        barTab.setContent(bc);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void createWind(boolean flag, Stage primaryStage) {
        /**Объект окна добавить или удалить компанию*/
        Stage newWindow;
        Label lab1 = new Label("Название");
        TextField nameComp = new TextField("название компании");
        ComboBox listComp=new ComboBox();
        listComp.getItems().setAll(listCompany);
        Button add;
        if(flag)add = new Button("Внести в список");
        else add = new Button("Удалить из списка");
        HBox second = new HBox();
        second.setSpacing(10);
        second.setPadding(new Insets(15, 20, 10, 10));
        if(flag)
            second.getChildren().addAll(lab1, nameComp, add);
        else
            second.getChildren().addAll(lab1, listComp, add);
        second.setStyle("-fx-font: 20 arial; -fx-base: #b6e7c9;");
        Scene secondScene = new Scene(second, 630, 150);
        newWindow = new Stage();
        if(flag)newWindow.setTitle("Добавление компании");
        else newWindow.setTitle("Удаление компании");
        newWindow.setScene(secondScene);
        newWindow.setX(primaryStage.getX() + 1300);
        newWindow.setX(primaryStage.getY() + 600);
        lab1.setLayoutX(40);
        lab1.setLayoutY(20);
        lab1.setMinWidth(100);
        nameComp.setMinWidth(500);
        nameComp.setLayoutX(40 + 2 * lab1.getWidth() + 10);
        nameComp.setLayoutY(40);
        nameComp.setMinWidth(200);
        add.setLayoutX(40 + 2 * lab1.getWidth() + 2 * nameComp.getWidth() + 10);
        add.setLayoutY(20);
        newWindow.show();
        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (flag) {
                    try {outstream = new FileWriter("company.txt", true);}
                    catch (IOException e1) {e1.printStackTrace();}
                    try { out=new FileWriter("CompanyOrder.txt", true); }
                    catch (IOException e) { e.printStackTrace(); }
                    if (nameComp.getText().length() != 0) {
                        listCompany.add(nameComp.getText());
                        comboBox1.getItems().setAll(listCompany);
                        try {outstream.append( nameComp.getText()+"\r\n");}
                        catch (IOException e) {e.printStackTrace();}
                        try {out.append( 0+"\r\n");}
                        catch (IOException e) {e.printStackTrace();}
                        try { outstream.close();} catch (IOException e) { e.printStackTrace(); }
                        try { out.close();} catch (IOException e) { e.printStackTrace(); }
                        countOrder.put(nameComp.getText(),0);
                        setBarTab(barTab);
                    }
                    else System.out.println("Пустое добавление");
                }
                else {
                    try {outstream= new FileWriter("company.txt");}
                    catch (IOException e1) {e1.printStackTrace();}
                    try { out=new FileWriter("CompanyOrder.txt", false); }
                    catch (IOException e) { e.printStackTrace(); }
                    if (((String)listComp.getValue()).length() != 0) {
                        listCompany.remove(listComp.getValue());
                        countOrder.remove(listComp.getValue());
                        comboBox1.getItems().setAll(listCompany);
                        for (String str : listCompany) {
                            try {outstream.write(str+"\r\n");}
                            catch (IOException e) {e.printStackTrace();}
                        }
                        Collection<Integer> mass = countOrder.values();
                        for (Integer i : mass)
                            try {out.write(String.valueOf(i)+"\r\n");} catch (IOException e) {e.printStackTrace();}
                        try { out.close();} catch (IOException e) { e.printStackTrace(); }
                        try { outstream.close();} catch (IOException e) { e.printStackTrace(); }
                        setBarTab(barTab);
                    }
                    else System.out.println("Пустое добавление");
                }
                newWindow.close();
            }
        });
    }
}