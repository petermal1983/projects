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
 *  ��������� ����� ��������� ������ ���� ���������, ����� ������� ����������� ������ � ����� �������
 *  ����������� ����� �������� ������� ������� ����������
 *  ������ ������ � ������� CSV
 * **/

public class TabSelect extends Application {

    /**������� ������ ��������� � �������������� �����*/
    private TextArea dgn = new TextArea();
    /**������ ���������� ��������*/
    private Button addCompany;
    /**������ �������� ��������*/
    private Button delCompany;
    /**������ �������� ����� ������ � ����*/
    private Button buttonEnter;
    /**���� ������������ �������� ��������*/
    private ComboBox<String> comboBox1;
    /**������ ����������� ������ ���� ��� ������*/
    private DropShadow effect = new DropShadow();
    private DropShadow shadow = new DropShadow();
    /**������� ������ �� ������ ������*/
    private FileReader instream, inst;
    /**������� ������ ������ � �����*/
    private static FileWriter outstream,out;
    /**������� �������� ���������� ������ �� ������*/
    private BufferedReader reader , rd;
    /**������ �������� ���������� ������ �� �������*/
    private BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream("Test.csv"),"Cp1251"));

    /**��������� ��� �������������� ��������� ������ ������� � ���� �������*/
    private GridPane root = new GridPane();
    /**������ ���� ���������*/
    private MenuBar  menuBar = new MenuBar();
    /**����� ������ menuBar*/
    private Menu menuF = new Menu("�����");
    /**������-���������� Menu � ��������� ����������, ������ �� ��������� � �������� ������� ������� ��������������*/
    private MenuItem itemSave = new MenuItem("���������");
    private MenuItem itemExit = new MenuItem("�����");
    private MenuItem itemOpen = new MenuItem("�������");
    /**������ �������� ������ ��������*/
    private ArrayList<Label> labelList;
    /**������ �������� ������ ����� �����*/
    private ArrayList<TextField> textFieldList;
    /**������ �������� ������ ������� �������*/
    private ArrayList<TableColumn> tableColumnList;
    /**������ �������� ������ �������� ��������*/
    private ArrayList<String> listCompany;
    /**������ �������� ������ �������� ������ Order(�������)*/
    private List<Order> Items;
    /**������ �������� ������� �������� ��������, ���������� �������*/
    private Map<String,Integer> countOrder=new HashMap<>();
    /**������ � ��������� - �������� ������� ������������ ���������*/
    TabPane tabPane = new TabPane();
    /**������ ����������, ������� ����������� ���� �������� ���� � �������, ������, ������, ����� � ����������� ������ ������*/
    BorderPane mainPane = new BorderPane();
    
    /**������� ���������� ������� � ���������*/
    Tab barTab = new Tab();
    /**������� ������� ������ ��������� - ���� ����� ������*/
    Tab tabA = new Tab();
    /**������� ������� ������ ��������� - ������� ����� �������*/
    Tab tabB = new Tab();
    /**������� ���������� ���������� �� ���������*/
    Tab tabD = new Tab();
    /**������� ���� ������ ��������� �����������*/
    Tab tabDg = new Tab();

    /**���������� ���������� ������ ������� ���������*/
    TableView tv1 = new TableView();
    /**������ ���������� �������� ����� � ������� �� �������� � ����� ����� � ����������� ���������*/
    GridPane pane = new GridPane();
    /**������ �������� � Label*/
    String[] nameColum={"�����������", "��������", "����������� ��������", "�����","���� � ����� �����������","���� � ����� ���������","������ �� ��", "�����"};
    /**������ ���� ���������� � TableColumn*/
    String[] nameValues= {"sender","name","dir","volume","StringReceived","StringReceived1","price","total"};
    /**������ �������� � TextField*/
    String[] nameTextField= {"������� �������� �����������","������� �������� ��������","������� �����������","������� �����","������� ���� ������","������� ���� �����","������� ������ �� ��������","�����"};

    public TabSelect() throws IOException {
    	/**������ ������ �������� ��������*/
        listCompany = new ArrayList<String>();
        /**�������� ������� ������ ������ �������� ������� �������� ��������*/
        instream = new FileReader("company.txt");
        /**�������� ������ ������ �������� ������� ������� ���������� ��������*/
        inst = new FileReader("CompanyOrder.txt");
        /**�������� ��������� ����� ����� �������� ������� ���������� ������ ������ �� ��������� ��� ���������� �� ������� ��� �������� ��������*/
        reader = new BufferedReader(instream);
        /**�������� ��������� ����� ����� �������� ������� ���������� ������ ������ �� ��������� ��� ���������� �� ������� ��� ������� ���������� �������� ��������*/
        rd= new BufferedReader(inst);
        /**������ ��������� ������ ��� �������� ��������*/
        String ln = reader.readLine();
        /**������ ��������� ������ ��� ������� ���������� �������� ��������*/
        String number = rd.readLine();
        /**������� �������� �� ������� ������ ������� �������� ��������*/
        while (ln != null)
        
        {
        	/**���������� ������ � ������ �������� ��������*/
            listCompany.add(ln);
            countOrder.put(ln,Integer.parseInt(number));
            /**������ ��������� ������ ��� �������� ��������*/
            ln = reader.readLine();
            /**������ ��������� ������ ��� ������� ���������� �������� ��������*/
            number=rd.readLine();
        }
        /**�������� ������ ���������� �� ������ �������� ��������*/
        instream.close();
        /**�������� ������ ���������� �� ������ ������� ���������� �������� ��������*/
        inst.close();
        /**���������� ������� ������ ������� �������*/
        Items = new ArrayList<>();
        /**�������� ��������� ������ ������������ ���������� ������ �� ����� �������� ������ ������� � ��������� ���������*/
        BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream("Test.csv"),"Cp1251"));
        /**���������� ��������� ������*/
        String line=br.readLine();
        /**������� �������� �� ������� ������*/
        while(line!=null) {
        	/**����� ����������� ������ � �������*/
        	System.out.println(line);
        	/**�������� ����������� ��������� � �������*/
        	String[] mass=line.split(";");
        	/**���������� � ������ ��������� ������ Order*/
        	Items.add(new Order(mass[0],mass[1],mass[2],Float.parseFloat(mass[3]),mass[4],mass[5],Integer.parseInt(mass[6]),(int)Float.parseFloat(mass[7])));
        	/**������ ��������� ������*/
        	line=br.readLine();
        }
        /**�������� ������ ���������� ��������*/
        addCompany = new Button("�������� ��������");
        /**������� ����� � ������ ������*/
        addCompany.setStyle("-fx-font: 30 arial; -fx-base: #b6e7c9;");
        /**�������� ������ �������� ������ � �������*/
        buttonEnter = new Button("�������");
        /**������� ������������ ������ �� ox*/
        buttonEnter.setLayoutX(100);
        /**������� ������������ ������ �� oy*/
        buttonEnter.setLayoutY(100);
        buttonEnter.setPrefSize(200, 80);
        buttonEnter.setStyle("-fx-font: 30 arial; -fx-base: #b6e7c9;");
        /**�������� ������ �������� ������*/
        delCompany = new Button("������� ��������");
        delCompany.setStyle("-fx-font: 30 arial; -fx-base: #b6e7c9;");

        /**�������� ����������� ������ ��������*/
        comboBox1 = new ComboBox<String>();
        comboBox1.setStyle("-fx-font: 20 arial; -fx-base: #b6e7c9;");
        /**������� ���������� ������������ ��������� ����������� ������*/
        comboBox1.setVisibleRowCount(10);
        comboBox1.setEditable(true);
        /**�������� � ���������� ���� ��������� ������� �������� ��������*/
        comboBox1.getItems().addAll(listCompany);
        /**�������� ������-��������� � ������� ������ ����������� ������*/
        comboBox1.setPromptText(nameTextField[0]);

        /**�������� ������������� ������������ ������� ������������ ����� ������ �������-������ ���������*/
        labelList=new ArrayList<Label>();
        /**���� � �������� - �� ���������� ��������� � �������*/
        for(int i=0; i<10; i++) {
        	/**���������� ������ ������������ ���� ��� ���������� �������*/
            if(i<8)labelList.add(new Label(nameColum[i]+":"));
            /**������� ���������� ������������ ������ ��� ���������� 9 �������� �������*/
            else if(i==8)labelList.add(new Label("��������� �������"));
            /**������� ���������� ������� ������������ ���� ��� 10 ��������*/
            else if(i==9)labelList.add(new Label(""));
            /**������� ������ ������������� �����*/
            labelList.get(i).setFont(new Font("Arial", 25));
            /**������� ����� ������� ������ ������������� �����*/
            labelList.get(i).setTextFill(Color.web("000000"));
            /**������� ���� ������������� �����*/
            labelList.get(i).setBackground(new Background(new BackgroundFill(Color.DARKSEAGREEN, new CornerRadii(10), Insets.EMPTY)));
        }
        /**�������� ������������� ������������ ������� ����� ����� ������ ������ �������-������ ���������*/
        textFieldList=new ArrayList<TextField>();
        /**���� � �������� - �� ���������� ��������� � �������*/
        for(int i=0; i<7; i++) {
        	/**���������� ������ ������������ ���� ��� ���������� �������*/
        	textFieldList.add(new TextField());
        	/**���������� � ���� ����� ������-���������*/
        	textFieldList.get(i).setPromptText(nameTextField[i+1]);
        	/**������� ������ ����� �����*/
        	textFieldList.get(i).setStyle(
                    "-fx-background-radius:10;-fx-border-radius:8;-fx-backgroundcolor:# ffefd5;-fx-border-width:3pt;-fx-border-color:#b6e7c9;-fxfontweight: bold;-fx-font-size:14pt; -fx-font-family:Georgia; -fx-fontstyle:italic");
            textFieldList.get(i).setPrefSize(600, 30);
            
        }
        /**�������� ������������� ������������ ������� ������� ������� ����� ������� ������ �������-������ ���������*/
        tableColumnList=new ArrayList<TableColumn>();
        /**���� � �������� - �� ���������� ��������� � �������*/
        for(int i =0; i<8; i++) {
        	/**������� ���� ���������� ������ ��� 5 �������*/
            if(i==4)	{tableColumnList.add(new TableColumn<Order,Float>(nameColum[i])); 		tableColumnList.get(i).setCellValueFactory(new PropertyValueFactory<Order, Float>(nameValues[i]));}
            /**������� ���� ���������� ������ ��� ������� ����� 6-�*/
            else if(i>5) {tableColumnList.add(new TableColumn<Order,Integer>(nameColum[i]));	tableColumnList.get(i).setCellValueFactory(new PropertyValueFactory<Order, Integer>(nameValues[i]));}
            /**������� ���� ���������� ������ ��� ��������� �������*/
            else {tableColumnList.add(new TableColumn<Order,String>(nameColum[i]));				tableColumnList.get(i).setCellValueFactory(new PropertyValueFactory<Order, String>(nameValues[i]));}
            tableColumnList.get(i).setPrefWidth(230);
            tableColumnList.get(i).setEditable(true);
            tableColumnList.get(i).setMinWidth(100);
            tabPane.setDisable(false);
        }

        root.setStyle("-fx-font: 30 arial; -fx-base: #b6e7c9;");
        /**���������� ���� � ��������� GridPane*/
        root.add(menuBar, 0, 0);

        effect.setOffsetX(1);
        effect.setOffsetY(1);

        /**������� ������ ��������� ����*/
        itemOpen.setStyle("-fx-text-fill:navy;-fx-font:bold 10pt Verdana;");
        /**������� ���������� ������ ��� ��������� ����*/
        itemOpen.setAccelerator(KeyCombination.keyCombination("Ctrl+O"));

        itemSave.setStyle("-fx-text-fill:navy;-fx-font:bold 10pt Verdana;");
        itemSave.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));

        itemExit.setStyle("-fx-text-fill:navy;-fx-font:bold 10pt Verdana;");
        itemExit.setAccelerator(KeyCombination.keyCombination("Ctrl+E"));

        /**���������� ��������� � ����*/
        menuF.getItems().addAll(itemOpen, itemSave, itemExit);

        menuBar.setEffect(effect);
        menuBar.setStyle("-fx-base:b6e7c9;-fx-border-width:0pt;-fx-border-color:navy;-fx-font:bold 15pt Verdana;");
        menuBar.setPrefSize(700, 20);
        menuBar.setBlendMode(BlendMode.HARD_LIGHT);
        menuBar.setCursor(Cursor.CLOSED_HAND);
        menuBar.getMenus().addAll(menuF);

        /**������� �������� �������*/
        barTab.setText("������ ����������� �������");
        /**������� ������ �������*/
        barTab.setStyle("-fx-font: 20 arial; -fx-base: #b6e7c9;");
        setBarTab(barTab);

        tv1.setEditable(true);
        tv1.setTableMenuButtonVisible(true);
        /**��������� �������� ������� ��� ������� �� �������*/
        tv1.getColumns().addAll(tableColumnList);
        /**��������� ����������� ����� ������� �� ������� Items*/
        tv1.setItems(FXCollections.observableArrayList(Items));

        /**������� ����-������� ��������� ������*/
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
        /**������� ��������� ������������ ��������� ���������� GridPane*/
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

        /**������� �������� ������� A*/
        tabA.setText("������ ������");
        /**������� ����� �������� ������� A*/
        tabA.setStyle("-fx-font: 20 arial; -fx-base: #b6e7c9;");
        /**������� ����������� ������� A*/
        tabA.setContent(pane);

        /**������� �������� ������� B*/
        tabB.setText("������� ������ �������");
        /**������� ����� �������� ������� B*/
        tabB.setStyle("-fx-font: 20 arial; -fx-base: #b6e7c9;");
        /**������� ����������� ������� B*/
        tabB.setContent(tv1);

        /**������� �������� ������� �����������*/
        tabDg.setText("�����������");
        /**������� ����� �������� ������� �����������*/
        tabDg.setStyle("-fx-font: 20 arial; -fx-base: #b6e7c9;");
        /**������� ����������� ������� �����������*/
        tabDg.setContent(dgn);

        /**������� ������� ������ � ���������*/
        tabPane.setBackground(new Background(myBF));

        /**������� �������� ������� �������*/
        tabD.setText("�������");
        /**������� ����� �������� ������� �������*/
        tabD.setStyle("-fx-font: 20 arial; -fx-base: #b6e7c9;");
        /**�������� ���������� ��������� ���-�������*/
        WebView browser = new WebView();
        /**�������� ������ ���������� ���-���������*/
        WebEngine webEngine = browser.getEngine();
        browser.setFontScale(2);
        /**��������� ���-�������� ������� �������������� �������*/
        webEngine.load(getClass().getResource("index.html").toString());
        /**������� ����������� ������� �������*/
        tabD.setContent(browser);

        tabPane.setTabClosingPolicy(TabClosingPolicy.ALL_TABS);
        tabA.setClosable(false);
        tabB.setClosable(false);
        barTab.setClosable(false);
        tabD.setClosable(false);
        tabDg.setClosable(false);

        /**���������� ������� �� ������ tabPane*/
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
        /**�������� ��� ������ ����� ������ � ������� ������� ������������ ���� �� ������� �������*/
        buttonEnter.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                buttonEnter.setEffect(shadow);
            }
        });
        /**���������� ������� ��� �������� ������ � ������� ����� �������*/
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
                try {tmpDate = formatpar.parse(dat1);} catch (ParseException e) {dgn.appendText("�������� ������ ����� ���� ���������\n");}
                Date tmpDate2 = new Date();
                try {tmpDate2 = formatpar.parse(dat2);} catch (ParseException e) {dgn.appendText("�������� ������ ����� ���� ��������\n");}
                try {vol = Float.parseFloat(volume);} catch (Exception e) {dgn.appendText("�������� ������ ����� ������ ������\n");}
                try {pric = Integer.parseInt(price);} catch (Exception e) {dgn.appendText("�������� ������ ����� ���������\n");}
                try {tot = Integer.parseInt(total);} catch (Exception e) {dgn.appendText("�������� ������ ����� �����\n");
                    labelList.get(9).setText("��������!,\n" + "���������, ����������,\n" + "������������ ����� ������!");}
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
        bc.setTitle("������ ������ �������");
        bc.setStyle("-fx-font: 30 arial; -fx-base: #b6e7c9;");
        xAxis.setLabel("�����������");
        xAxis.setStyle("-fx-font: 30 arial; -fx-base: #b6e7c9;");
        yAxis.setLabel("����������");
        yAxis.setStyle("-fx-font: 30 arial; -fx-base: #b6e7c9;");
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("����� �������");
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
        /**������ ���� �������� ��� ������� ��������*/
        Stage newWindow;
        Label lab1 = new Label("��������");
        TextField nameComp = new TextField("�������� ��������");
        ComboBox listComp=new ComboBox();
        listComp.getItems().setAll(listCompany);
        Button add;
        if(flag)add = new Button("������ � ������");
        else add = new Button("������� �� ������");
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
        if(flag)newWindow.setTitle("���������� ��������");
        else newWindow.setTitle("�������� ��������");
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
                    else System.out.println("������ ����������");
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
                    else System.out.println("������ ����������");
                }
                newWindow.close();
            }
        });
    }
}