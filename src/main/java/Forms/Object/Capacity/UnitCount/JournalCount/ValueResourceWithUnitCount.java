package Forms.Object.Capacity.UnitCount.JournalCount;

import Forms.Object.Capacity.UnitCount.UnitCountUnInstall;
import Forms.Service.DialogWindow;
import Service.ActInstallUnitCountDAOImp;
import Service.Exception.*;
import Service.JournalUnitCountDAOImpl;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
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
    private Label model, number, labelPreviewCount, labelValue, labelDescription, labelNextCount;
    private double previewCount, value;
    public double currentCountDouble;
    private TextField currentCount;
    public static double balanceResource, countValueSource,countValueConsumer;
    private String objectName, cssDefault, cssLabel;
    public boolean source;
    private BorderPane borderPane;
    private Button deleteUnitCount;
    private ActInstallCountEntity actInstallCountEntity;
    private CapacitySourceObjectEntity capacitySourceObjectEntity;
    private JournalUnitCountEntity journalUnitCountEntity, journalUnitCountEntityNext;

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

        labelPreviewCount = new Label(String.valueOf(getPreviewCount()));
        labelPreviewCount.setMinWidth(50);
        labelPreviewCount.setStyle(cssLabel);

        currentCount = new TextField();
        currentCount.setText(String.valueOf(getCurrentCountDouble()));
        currentCount.setMaxWidth(110);
        currentCount.setAlignment(Pos.CENTER);
        currentCount.setStyle(cssLabel);

        labelNextCount = new Label((getNextCount() == 0)?"":String.valueOf(getNextCount()));
        labelNextCount.setMinWidth(50);
        labelNextCount.setStyle(cssLabel);

        HBox bottomHBox = new HBox();
        bottomHBox.getChildren().add(labelPreviewCount);
        bottomHBox.getChildren().add(currentCount);
        bottomHBox.getChildren().add(labelNextCount);
        HBox boxValueResource = new HBox(labelValueResource());
        boxValueResource.setAlignment(Pos.BASELINE_RIGHT);
        VBox bottomVBox = new VBox(bottomHBox, boxValueResource);

        borderPane = new BorderPane();
        borderPane.setLeft(leftVBox);
        borderPane.setRight(rightHBox);
        borderPane.setBottom(bottomVBox);
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
     * Возвращает предыдущее или текущее показание узла учета
     * @return double
     */
    private double getPreviewCount(){
      //  NavigableMap<Date, Double> countMap = new TreeMap<>();
                //должен возвращать последнее показание перед текущей датой
            JournalUnitCountDAOImpl journalUnitCountDAO = new JournalUnitCountDAOImpl();
        try {
            if(journalUnitCountDAO.getPreviewCountToJournal(actInstallCountEntity)== null) {
                throw new NullCountToJournal();
            }else {
                journalUnitCountEntityNext = journalUnitCountDAO.getPreviewCountToJournal(actInstallCountEntity);
                if (actInstallCountEntity.getDateInstall().after(journalUnitCountEntityNext.getDateCount())) {
                    previewCount = actInstallCountEntity.getFirstCountValue();
                } else {
                    previewCount = journalUnitCountEntityNext.getCountUnit();
                }
            }
        }catch (NullCountToJournal e){
            previewCount = actInstallCountEntity.getFirstCountValue();

        }catch (NullPointerException e){
            System.out.println(e + "ошибка в ValueResourceWithUnitCount ");
        }
        return previewCount;
    }

    private double getCurrentCountDouble(){
        JournalUnitCountDAOImpl journalUnitCountDAO = new JournalUnitCountDAOImpl();
        try{
            if(journalUnitCountDAO.getCurrentJournal(actInstallCountEntity) == null){
                currentCountDouble = previewCount;
                throw new CurrentCountNull();
            }else{
                JournalUnitCountEntity journalUnitCountEntityCurrent = journalUnitCountDAO.getCurrentJournal(actInstallCountEntity);
                this.journalUnitCountEntity = journalUnitCountEntityCurrent;
                currentCountDouble = journalUnitCountEntityCurrent.getCountUnit();
            }
        }catch(CurrentCountNull e){
            currentCount.setText(String.valueOf(previewCount));
        }
        value = currentCountDouble - previewCount;
        if (source == true) {
            countValueSource += value;
            JournalAddNewCountOrValue.labelSourceValue.setText(String.valueOf(countValueSource));
            balanceResource = countValueSource - countValueConsumer;
            JournalAddNewCountOrValue.labelBalanceResource.setText(String.valueOf(balanceResource));
        } else if (source == false) {
            countValueConsumer += value;
            JournalAddNewCountOrValue.labelConsumerValue.setText(String.valueOf(countValueConsumer));
            balanceResource = countValueSource - countValueConsumer;
            JournalAddNewCountOrValue.labelBalanceResource.setText(String.valueOf(balanceResource));
        }
        return currentCountDouble;
    }

    private double getNextCount(){
        double nextCount = 0;
        JournalUnitCountDAOImpl journalUnitCountDAO = new JournalUnitCountDAOImpl();
        try {
            if(journalUnitCountDAO.getNextCount(actInstallCountEntity) == null){
                throw new NextCountIsNull();
            }else {
                JournalUnitCountEntity journalUnitCountEntityNext = journalUnitCountDAO.getNextCount(actInstallCountEntity);
                nextCount = journalUnitCountEntityNext.getCountUnit();
            }
        }catch (NullPointerException e){
            System.out.println(e + " exception when find NextCount to ValueResourceWithCount.class");
        } catch (NextCountIsNull nextCountIsNull) {

        }
        return nextCount;
    }

    public Label labelValueResource(){
        labelValue = new Label();
        labelValue.setStyle(cssLabel);
        labelValue.setText(String.valueOf(value));

        this.currentCount.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                try {


                    if (newValue == true && source == true){
                        countValueSource -= value;
                    } else if(newValue == true && source == false){
                        countValueConsumer -= value;
                    }
                    if (oldValue == true){
                        currentCountDouble = Double.parseDouble(currentCount.getText());
                        //Проверяю чтобы показание текущее не было больше показания следующей даты
                        if(!labelNextCount.getText().equals("") && currentCountDouble > getNextCount()){
                            throw new CurrentCountHighNext();
                        }
                        //И на оборот
                        if(currentCountDouble < previewCount && newValue == false){
                            throw new CurrentCountLowPreview();
                        }
                        value = currentCountDouble - previewCount;
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
                    } catch (CurrentCountHighNext currentCountHighNext) {
                        DialogWindow dialogWindow = new DialogWindow("Текущее показание больше следующего");
                        currentCount.setText(String.valueOf(previewCount));
                    } catch (CurrentCountLowPreview e){
                        DialogWindow dialogWindow = new DialogWindow("Текущее показание меньше предыдущего");
                        currentCount.setText(String.valueOf(previewCount));
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


    public void setPreviewCount(double lastCount) {
        this.previewCount = lastCount;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Label getLabelNextCount() {
        return labelNextCount;
    }

    public void setLabelNextCount(Label labelNextCount) {
        this.labelNextCount = labelNextCount;
    }
    public double getCurrentCount(){
        return currentCountDouble;
    }

    public JournalUnitCountEntity getJournalUnitCountEntityNext() {
        return journalUnitCountEntityNext;
    }

    public void setJournalUnitCountEntityNext(JournalUnitCountEntity journalUnitCountEntityNext) {
        this.journalUnitCountEntityNext = journalUnitCountEntityNext;
    }
}
