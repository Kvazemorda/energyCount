package Forms.Object.Capacity.UnitCount.JournalCount;

import Forms.Object.Capacity.UnitCount.UnitCountUnInstall;
import Forms.Service.DialogWindow;
import Service.ActInstallUnitCountDAOImp;
import Service.Exception.NullCountToJournal;
import Service.JournalUnitCountDAOImpl;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import vankor.EnergyDepartment.CapacitySourceObjectEntity;
import vankor.EnergyDepartment.WriteDataUnitCountToJournal.ActInstallCountEntity;
import vankor.EnergyDepartment.WriteDataUnitCountToJournal.JournalUnitCountEntity;
import vankor.EnergyDepartment.WriteDataUnitCountToJournal.UnitCountEntity.UnitCountEntity;

/**
 * Клас создает Пнель с данными узла учета и полем для ввода показаний УУ
 */
public class ValueResourceWithUnitCount implements Comparable{

    public UnitCountEntity unitCountEntity;
    private Label model, number, labelLastCount, labelValue, labelDescription;
    private double lastCount, value;
    public double currentCountDouble;
    private TextField currentCount;
    public static double balanceResource, countValueSource,countValueConsumer;
    private String objectName, cssDefault, cssLabel;
    public boolean source;
    private BorderPane borderPane;
    private Button deleteUnitCount;
    private ActInstallCountEntity actInstallCountEntity;
    private CapacitySourceObjectEntity capacitySourceObjectEntity;
    private JournalUnitCountEntity journalUnitCountEntity;

    public ValueResourceWithUnitCount(CapacitySourceObjectEntity capacitySourceObjectEntity) {
        this.capacitySourceObjectEntity = capacitySourceObjectEntity;
        ActInstallUnitCountDAOImp actInstallUnitCountDAOImp = new ActInstallUnitCountDAOImp();
        this.actInstallCountEntity = actInstallUnitCountDAOImp.getActInstallCount(capacitySourceObjectEntity);
    }


    public BorderPane createPaneUnitCount(){
        cssDefault = "-fx-padding: 10px, 12px;";
        cssLabel = "-fx-padding: 4px,4px; ";
        model = new Label(unitCountEntity.getModel());
        model.setStyle(cssLabel);
        labelDescription = new Label(actInstallCountEntity.getDescription());
        labelDescription.setStyle(cssLabel);
        VBox leftVBox = new VBox(model, labelDescription);
        number = new Label(unitCountEntity.getNumber());
        number.setStyle(cssLabel);
        deleteUnitCount = new Button("-");
        deleteUnitCount.setMaxSize(4, 4);
        HBox rightHBox = new HBox();
        rightHBox.getChildren().add(number);
        rightHBox.getChildren().add(deleteUnitCount);
        deleteUnitCountListener();

        labelLastCount = new Label(String.valueOf(getLastCount()));
        labelLastCount.setMinWidth(60);
        labelLastCount.setStyle(cssLabel);

        HBox bottomHBox = new HBox();
        bottomHBox.getChildren().add(labelLastCount);
        bottomHBox.getChildren().add(currentCount);
        bottomHBox.getChildren().add(labelValueResource());

        borderPane = new BorderPane();
        borderPane.setLeft(leftVBox);
        borderPane.setRight(rightHBox);
        borderPane.setBottom(bottomHBox);
        borderPane.setStyle(cssDefault);
        return borderPane;
    }
    public void deleteUnitCountListener(){
        deleteUnitCount.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            UnitCountUnInstall unitCountUnInstall = new UnitCountUnInstall(actInstallCountEntity);
            unitCountUnInstall.setModel(unitCountEntity.getModel());
            unitCountUnInstall.setNumber(unitCountEntity.getNumber());
            unitCountUnInstall.setObject(getObjectName());
            unitCountUnInstall.createFormUnInstallUnitCount();
            unitCountUnInstall.setActInstallCountEntity(actInstallCountEntity);
        });
    }

    /**
     * Возвращает последнее показание узла учета
     * @return double
     */
    private double getLastCount(){
        currentCount = new TextField();
        currentCount.setMaxWidth(110);
        currentCount.setStyle(cssLabel);
        lastCount = 0;
      //  NavigableMap<Date, Double> countMap = new TreeMap<>();
                //должен возвращать последнее показание перед текущей датой
            JournalUnitCountDAOImpl journalUnitCountDAO = new JournalUnitCountDAOImpl();
            JournalUnitCountEntity journalUnitCountEntity1 = journalUnitCountDAO.getLastCountToJournal(actInstallCountEntity);
        try {
            if(journalUnitCountDAO.getLastCountToJournal(actInstallCountEntity)== null) {
                System.out.println("Последнее показание в журнале" + journalUnitCountEntity1.getCountUnit() + journalUnitCountEntity1.getDateCount());
                throw new NullCountToJournal();
            }else {
                if (actInstallCountEntity.getDateInstall().after(journalUnitCountEntity1.getDateCount())) {
                    lastCount = actInstallCountEntity.getFirstCountValue();
                } else {
                    lastCount = journalUnitCountEntity1.getCountUnit();
                }
                this.journalUnitCountEntity = journalUnitCountEntity1;
            }
        }catch (NullCountToJournal e){
            lastCount = actInstallCountEntity.getFirstCountValue();

        }catch (NullPointerException e){
            System.out.println(e + "ошибка в ValueResourceWithUnitCount ");
        }
        currentCount.setText(String.valueOf(lastCount));
        return lastCount;
    }

    public Label labelValueResource(){
        labelValue = new Label();
        labelValue.setStyle(cssLabel);
        labelValue.setText(String.valueOf(value));
        currentCount.setText(String.valueOf(lastCount));
        this.currentCount.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                try {
                    if (newValue == true && source == true){
                        countValueSource -= value;
                    } else if(newValue == true && source == false){
                        countValueConsumer -= value;
                    }
                    if (oldValue==true){
                        currentCountDouble = Double.parseDouble(currentCount.getText());
                        value = currentCountDouble - lastCount;
                    }
                        if (source == true && oldValue == true) {
                            countValueSource += value;
                            JournalAddNewCountOrValue.labelSourceValue.setText(String.valueOf(countValueSource));
                            balanceResource = countValueSource - countValueConsumer;
                            JournalAddNewCountOrValue.labelBalanceResource.setText(String.valueOf(balanceResource));
                        } else if (source == false && oldValue == true) {
                            countValueConsumer += value;
                            JournalAddNewCountOrValue.labelConsumerValue.setText(String.valueOf(countValueConsumer));
                            balanceResource = countValueSource - countValueConsumer;
                            JournalAddNewCountOrValue.labelBalanceResource.setText(String.valueOf(balanceResource));
                        }
                    } catch (NumberFormatException exp) {
                        System.out.println(exp);
                        DialogWindow dialogWindow = new DialogWindow("Показание должно быть числом");
                        currentCount.setText(lastCount + "");
                    }
                    labelValue.setText(String.valueOf(value));
                }
        });
        return labelValue;
    }

    public UnitCountEntity getUnitCountEntity() {
        return unitCountEntity;
    }

    public void setUnitCountEntity(UnitCountEntity unitCountEntity) {
        this.unitCountEntity = unitCountEntity;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getCssDefault() {
        return cssDefault;
    }

    public void setCssDefault(String cssDefault) {
        this.cssDefault = cssDefault;
    }

    public String getCssLabel() {
        return cssLabel;
    }

    public void setCssLabel(String cssLabel) {
        this.cssLabel = cssLabel;
    }

    public ActInstallCountEntity getActInstallCountEntity() {
        return actInstallCountEntity;
    }

    public void setActInstallCountEntity(ActInstallCountEntity actInstallCountEntity) {
        this.actInstallCountEntity = actInstallCountEntity;
    }

    @Override
    public int compareTo(Object o) {

        ValueResourceWithUnitCount valueResourceWithUnitCount = (ValueResourceWithUnitCount) o;

        return this.getObjectName().compareTo(valueResourceWithUnitCount.getObjectName());
    }

    public JournalUnitCountEntity getJournalUnitCountEntity() {
        return journalUnitCountEntity;
    }

    public void setJournalUnitCountEntity(JournalUnitCountEntity journalUnitCountEntity) {
        this.journalUnitCountEntity = journalUnitCountEntity;
    }


    public void setLastCount(double lastCount) {
        this.lastCount = lastCount;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
