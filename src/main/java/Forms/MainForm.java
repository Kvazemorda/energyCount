package Forms;

import Forms.Object.Capacity.CapacityNetWork;
import Forms.Object.Capacity.UnitCount.JournalCount.JournalAddNewCountOrValue;
import Forms.Object.CapacityConnectToObject;
import Forms.Object.ObjectAddNewOnPlace;
import Forms.Owner.ContractAddNew;
import Forms.Service.DialogWindow;
import Forms.Service.FirstSet;
import Forms.Users.UserAddNew;
import Service.HibernateSessionFactory;
import Service.Messages.CheckMail;
import Service.Messages.MyExchangeService;
import Service.UsersDAOImp;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.hibernate.Session;
import vankor.EnergyDepartment.WriteDataUnitCountToJournal.UsersEntity;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс из которого созадется основное окно приложения.
 * @link MainForm#createDatePicker() - вверху создается календарь
 * @link MainForm#createTreeView() - дерево доступных модулей. Модули открываются во вкладках в центре
 * @link MainForm#createTabPane() - вкладки модулей
 * @autor Khokhlov Il'ya
 * ilyavanavara@hotmail.com, +7-923-317-8678
 * @version 1.0
 */
public class MainForm extends Application{
    public static Date currentDate;
    public static Session session;
    private Scene scene;
    private BorderPane borderPane;
    public TabPane tabPane;
    private Tab tabReports, tabReportsCTVS, tabAddNewObject, tabConnectObjectByResource, tabAddNewContract, tabCreateNetWork;
    private MenuBar menuBar;
    private String reports, reportCTVS, addNewObject, connectObjectToResource, addNewContract, createNetWork;
    public static JournalAddNewCountOrValue journalAddNewCountOrValue;
    private static TreeItem<String> treeReports, treeReportCTVS, treeItemAddNewObject, treeItemConnectObjByResource,
            treeAddNewContract, treeCreateNetWork;
    private Map<TreeItem<String>, Tab> mapTabs;
    public static DatePicker datePicker;
    public static Button updateFormsButton;
    public static Boolean outlookIsConnected = true;
    public static MyExchangeService myExchangeService;
    public static ImageView outlookConnectIcon;



    public static void main(String[] args) {
            //start Hibernate
            MainForm.session = HibernateSessionFactory.getSessionFactory().openSession();

            UsersDAOImp usersDAOImp = new UsersDAOImp();
            UsersEntity usersEntity;
            usersEntity = usersDAOImp.getCurrentUser(System.getProperty("user.name"));

            myExchangeService = new MyExchangeService();

            Thread thread = new Thread(new CheckMail(myExchangeService.service));
            thread.setDaemon(true);
            thread.start();

            // check has user into dataBase
            if (usersEntity != null) {
                launch(args);
                MainForm.session.close();
                HibernateSessionFactory.shutdown();
            }
            String s =
                    "\n Добавить Enum видов отчетов " +
                            "\n Привязать Enum к ресурсам " +
                            "\n При Открытии отчета ЦТВС сделать грид из всех видов ресорсов отчета " +
                            "\n В каждый грид внести источинки и потребители " +
                            "\n Свод объема по каждому ресурсу и свод баланса от Root до самого низжешго " +
                    "\n Сделать форму привязки потребителя к ресурсу " +
                    "\n При первом запуске вывести настрйку почты и провести сериализацию из почты " +
                    "\n Если нет пользователя то вывести сообщение о том что вы не зарегистрированны и обратитесь по эл. почте" +
                    "\n При запуске программы провести сериализацию из почты " +
                    "\n В классе serializableAndSendMail изменить имя пользователя на user.name" +
                    "\n Собрать сети водоснабжения водоотведения " +
                    "\n Тестировать сети по отчету ЦТВС " +
                    "\n Добавить в поле настроек дату формирования отчета " +
                    "\n Сделать шаблон формирования справок за период " +
                    "\n Сделать шаблон формирования актов выполненных работ за период " +
                    "\n Внедрить прогу :-)";

                   /*
                   Version 2.0
                    1. Если почта не подключается то сделать отметку что почта не подключена и все сохранения пометить как не отправленные.
                    2. После подклчения почты сделать отправку всех не сохраненных элементов.
                    3. Если за текущую дату период счтается закрытым, то исправлять показание запрещено

                    " Не использовать объемы в показаниях узла учета, для расчета объемов. Считать объем через разница показаний \n" +
                    "сегодняшнего от предыдущего отчета \n";*/

            System.out.println(s);
    }

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        root.setAutoSizeChildren(true);
        root.getChildren().add(createBorderPane());
        scene = new Scene(root, 980,800);
        borderPane.prefHeightProperty().bind(scene.heightProperty());
        borderPane.prefWidthProperty().bind(scene.widthProperty());
        menuBar.prefWidthProperty().bind(scene.widthProperty());
        primaryStage.setTitle("Управление энергетики, отчеты ЦТВС");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    /**
     * Верхнее меню
     * @return MenuBar
     */
    private MenuBar createTopMenu(){
        menuBar = new MenuBar();
        Menu menu = new Menu("About");
        MenuItem menuItem = new MenuItem("Настройки почты");
        Menu set = new Menu("Настройки");
        Menu addNewUser = new Menu("Добавить нового пользователя");
        set.getItems().add(menuItem);
        set.getItems().add(addNewUser);
        menuBar.getMenus().add(set);
        menuBar.getMenus().add(menu);

        menuItem.onActionProperty().setValue(v ->{
            FirstSet firstSet = new FirstSet();
        });
        addNewUser.onActionProperty().setValue(v->{
            UserAddNew userAddNew = new UserAddNew();
        });
       // checkConnectedToOutlook();
        return menuBar;
    }

    private void checkConnectedToOutlook(){
        if (outlookIsConnected == false){
            FirstSet firstSet = new FirstSet();
        }
    }

    /**
     * Основной бордер в нем располагаются все боксы
     * @return BorderPane
     */
    private BorderPane createBorderPane(){
        updateFormsButton = new Button("",new ImageView("file:src/main/Icons/updates.png"));
        outlookConnectIcon = new ImageView("file:src/main/Icons/disconnect.png");
        outlookConnectIcon.setY(-10);
        HBox bottomHBox = new HBox(outlookConnectIcon);
        bottomHBox.setSpacing(10);
        bottomHBox.setPadding(new Insets(0,10,0,0));
        bottomHBox.setAlignment(Pos.BASELINE_RIGHT);
        bottomHBox.setBackground(Background.EMPTY);
        if(MainForm.outlookIsConnected == false){
            outlookConnectIcon.setImage(new Image("file:src/main/Icons/disconnect.png"));
        }else{
            outlookConnectIcon.setImage(new Image("file:src/main/Icons/connected-128.png"));
        }
        HBox hBox = new HBox();
        HBox hBoxMenu = new HBox();
        hBoxMenu.getChildren().add(createDatePicker());
        hBoxMenu.getChildren().add(createTopMenu());
        hBox.getChildren().add(createTreeView());
        borderPane = new BorderPane();
        borderPane.setTop(hBoxMenu);
        borderPane.setLeft(hBox);
        borderPane.setCenter(tabPane);
        borderPane.setBottom(bottomHBox);
        borderPane.setAlignment(bottomHBox, Pos.BASELINE_RIGHT);
        return borderPane;
    }

    /**
     * Дерево всех доступных меню с сылкой на открытие меню во вкладке
     * @return TreeView
     */
    private TreeView<TreeItem> createTreeView(){
        createTabPane();
        treeReports = new TreeItem(tabReports.getText());
        treeReportCTVS = new TreeItem(tabReportsCTVS.getText());
        treeItemAddNewObject = new TreeItem(tabAddNewObject.getText());
        treeItemConnectObjByResource = new TreeItem(tabConnectObjectByResource.getText());
        treeAddNewContract = new TreeItem(tabAddNewContract.getText());
        treeCreateNetWork = new TreeItem<>(tabCreateNetWork.getText());
        mapTabs = new HashMap<>();
        mapTabs.put(treeReports, tabReports);
        mapTabs.put(treeReportCTVS, tabReportsCTVS);
        mapTabs.put(treeItemAddNewObject, tabAddNewObject);
        mapTabs.put(treeItemConnectObjByResource, tabConnectObjectByResource);
        mapTabs.put(treeAddNewContract, tabAddNewContract);
        mapTabs.put(treeCreateNetWork, tabCreateNetWork);
        treeReports.setExpanded(true);
        treeReports.getChildren().add(0,treeReportCTVS);
        treeReports.getChildren().add(1,treeItemAddNewObject);
        treeReports.getChildren().add(2,treeItemConnectObjByResource);
        treeReports.getChildren().add(3,treeAddNewContract);
        treeReports.getChildren().add(4, treeCreateNetWork);
        final TreeView<TreeItem> treeView = new TreeView(treeReports);
        treeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<TreeItem>>() {
            @Override
            public void changed(ObservableValue<? extends TreeItem<TreeItem>> observable, TreeItem<TreeItem> oldValue, TreeItem<TreeItem> newValue) {
                try {
                    if (!tabPane.getTabs().contains(mapTabs.get(newValue))) {
                        tabPane.getTabs().add(mapTabs.get(newValue));
                        tabPane.getSelectionModel().select(mapTabs.get(newValue));
                    } else {
                        tabPane.getSelectionModel().select(mapTabs.get(newValue));
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        });
        return treeView;
    }

    /**
     * Панель вкладок всех меню:
     * @see JournalAddNewCountOrValue#createPane() - создается вкладка "Отчет ЦТВС" с формой ввода показаний узлов учет
     * и в случае отсутсвия узлов учета вводятся показания часов работы оборудования (считается объем по наработке)
     * @see ObjectAddNewOnPlace#createBorderPane() - создается вкладка "Создать новый объект" в которой добавляются
     * новые объекты на площадку с обязательным подключением к ресурсу.
     * @see CapacityConnectToObject#createCapacityConnectToObject() - создается вкладка "Подключить/Откл. ресурс к объекту",
     * где можно добавить новую мощность к объекту или удалить старую.
     * @return TabPane
     */
    private TabPane createTabPane() {
        reportCTVS = "Отчет ЦТВС";
        reports = "Отчеты";
        addNewObject = "Создать новый объект";
        connectObjectToResource = "Подкл./откл. ресурс к объекту";
        addNewContract = "Добавить новый договор";
        createNetWork = "Соединяем сети";
        tabPane = new TabPane();
        tabReports = new Tab(reports);
        tabReportsCTVS = new Tab(reportCTVS);
        tabConnectObjectByResource = new Tab(connectObjectToResource);
        tabAddNewObject = new Tab(addNewObject);
        tabAddNewContract = new Tab(addNewContract);
        tabCreateNetWork = new Tab(createNetWork);
        journalAddNewCountOrValue = new JournalAddNewCountOrValue();
        ObjectAddNewOnPlace objectAddNewOnPlace = new ObjectAddNewOnPlace();
        CapacityConnectToObject capacityConnectToObject = new CapacityConnectToObject();
        tabReports.setContent(new Label("Здесь будут отчеты"));
        tabReportsCTVS.setContent(journalAddNewCountOrValue.createPane());
        tabAddNewObject.setContent(objectAddNewOnPlace.createBorderPane());
        tabConnectObjectByResource.setContent(capacityConnectToObject.createCapacityConnectToObject());
        ContractAddNew contractAddNew = new ContractAddNew();
        tabAddNewContract.setContent(contractAddNew.getBorderPaneBasicForm());
        CapacityNetWork capacityNetWork = new CapacityNetWork();
        tabCreateNetWork.setContent(capacityNetWork.getBorderPane());
        return tabPane;
    }

    /**
     * Календарь. Начинается  с сегодняшнего дня и любые действия с объектами записываются датой указанной в календаре.
     * @return DatePecker
     */
    public static DatePicker createDatePicker(){
        datePicker = new DatePicker(LocalDate.now());
        datePicker.setMinWidth(248);
        LocalDate localDate = datePicker.getValue();
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        currentDate = Date.from(instant);
        datePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            LocalDate localDate1 = datePicker.getValue();
            Instant instant1 = Instant.from(localDate1.atStartOfDay(ZoneId.systemDefault()));
            currentDate = Date.from(instant1);
        });
        return datePicker;
    }

    public static Date getCurrentDate() {
        return currentDate;
    }

    /**
     * Если почта отключается то меняется иконка в углу окна
     */
    public static void setOutlookIsConnected(){
        if(MainForm.outlookIsConnected == false){
            outlookConnectIcon.setImage(new Image("file:src/main/Icons/disconnect.png"));
            DialogWindow dialogWindow = new DialogWindow("Почта отключена");
        }else{
            outlookConnectIcon.setImage(new Image("file:src/main/Icons/connected-128.png"));
        }
    }
}