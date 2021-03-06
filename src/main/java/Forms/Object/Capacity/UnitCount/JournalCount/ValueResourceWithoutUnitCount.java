package Forms.Object.Capacity.UnitCount.JournalCount;

import Forms.MainForm;
import Forms.Service.DialogWindow;
import Service.Exception.HoursWorkException;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import vankor.EnergyDepartment.CapacitySourceObjectEntity;
import vankor.EnergyDepartment.WriteDataUnitCountToJournal.JournalOtherMethodEntity;

/**
 * Создает формы с мощностью по наработке для объектов без узлов учета
 */
public class ValueResourceWithoutUnitCount {
    private CapacitySourceObjectEntity capacitySourceObjectEntity;
    private HBox hBox1,hBox2,hBox3;
    private TextField tfWorkHours;
    public double valueOfPeriod = 0;
    private Label labelValueOfPeriod, labelDescription;
    private double workHours;
    public boolean source = false;
    private JournalOtherMethodEntity journalOtherMethodEntityCurrent;
    private String background = "-fx-background-color: #f6f7c5";

    public ValueResourceWithoutUnitCount(CapacitySourceObjectEntity capacitySourceObjectEntity) {
        this.capacitySourceObjectEntity = capacitySourceObjectEntity;

    }

    public VBox createForm(){
        tfWorkHours = new TextField("24");
        tfWorkHours.setMaxWidth(35);
        tfWorkHours.setAlignment(Pos.TOP_CENTER);
        tfWorkHours.setStyle("-fx-padding: 4px,4px;");
        hBox1 = new HBox(new Label(capacitySourceObjectEntity.getDescription() + "-"), new Label(capacitySourceObjectEntity.getCapacity() + " в час"));
        hBox1.setSpacing(2);
        hBox1.setStyle("-fx-padding: 5");
        labelDescription = new Label(capacitySourceObjectEntity.getContractEntity().toString());
        labelDescription.setStyle("-fx-padding: 5");
        workHours = Double.parseDouble(tfWorkHours.getText());
        valueOfPeriod = capacitySourceObjectEntity.getCapacity() * workHours;
        labelValueOfPeriod = new Label(valueOfPeriod + "");
        labelValueOfPeriod.setStyle("-fx-font-scale: 15");
        hBox3 = new HBox(new Label("итого объем: "), labelValueOfPeriod);
        hBox2 = new HBox(new Label("Работал "), tfWorkHours, new Label(" ч. "), hBox3);
        hBox2.setStyle("-fx-padding: 5");
        journalOtherMethodEntityCurrent = new JournalOtherMethodEntity(MainForm.currentDate, workHours, valueOfPeriod, capacitySourceObjectEntity);
        VBox vBox = new VBox(labelDescription, hBox1, hBox2);
        vBox.setStyle("-fx-font-smoothing-type: #d6d6d6; -fx-padding: 6");
        vBox.setStyle(background);
        changeWorkHours();
        addValueToBalance();
        return vBox;
    }

    private void addValueToBalance(){
        if (source == true){
            ValueResourceWithUnitCount.countValueSource += valueOfPeriod;
            JournalAddNewCountOrValue.labelSourceValue.setText(ValueResourceWithUnitCount.countValueSource + "");
        }else{
            ValueResourceWithUnitCount.countValueConsumer += valueOfPeriod;
            JournalAddNewCountOrValue.labelConsumerValue.setText(ValueResourceWithUnitCount.countValueConsumer + "");
        }
    }

    private void changeValueToBalance(){
        if (source == true){
            ValueResourceWithUnitCount.countValueSource -= valueOfPeriod;
            JournalAddNewCountOrValue.labelSourceValue.setText(ValueResourceWithUnitCount.countValueSource + "");
        }else{
            ValueResourceWithUnitCount.countValueConsumer -= valueOfPeriod;
            JournalAddNewCountOrValue.labelConsumerValue.setText(ValueResourceWithUnitCount.countValueConsumer + "");
        }
    }

    public void changeWorkHours(){
        tfWorkHours.focusedProperty().addListener((observable, oldValue, newValue) -> {
            try {
                workHours = Double.parseDouble(tfWorkHours.getText());
                if (workHours > 24 || workHours < 0){
                    throw new HoursWorkException();
                }else {
                    changeValueToBalance();
                    valueOfPeriod = capacitySourceObjectEntity.getCapacity() * workHours;
                    labelValueOfPeriod.setText(valueOfPeriod + "");
                    addValueToBalance();
                    ValueResourceWithUnitCount.balanceResource = ValueResourceWithUnitCount.countValueSource - ValueResourceWithUnitCount.countValueConsumer;
                    JournalAddNewCountOrValue.labelBalanceResource.setText(String.valueOf(ValueResourceWithUnitCount.balanceResource));
                }
                journalOtherMethodEntityCurrent.setTimeWork(workHours);
                journalOtherMethodEntityCurrent.setValue(valueOfPeriod);
                journalOtherMethodEntityCurrent.setDate(MainForm.currentDate);
            }
            catch (NumberFormatException e){
                tfWorkHours.setText("24");
                System.out.println(e);
                String text = "В поле час должно быть число.";
                DialogWindow dialogWindow = new DialogWindow(text);
            } catch (HoursWorkException e) {
                String text = "Не больше 24 часов в сутки. \n" +
                        " и не меньше 0";
                DialogWindow dialogWindow = new DialogWindow(text);
                tfWorkHours.setText("24");
            }
        });
    }

    public JournalOtherMethodEntity getJournalOtherMethodEntityCurrent() {
        return journalOtherMethodEntityCurrent;
    }

    public void setJournalOtherMethodEntityCurrent(JournalOtherMethodEntity journalOtherMethodEntityCurrent) {
        this.journalOtherMethodEntityCurrent = journalOtherMethodEntityCurrent;
    }

    public CapacitySourceObjectEntity getCapacitySourceObjectEntity() {
        return capacitySourceObjectEntity;
    }

    public void setCapacitySourceObjectEntity(CapacitySourceObjectEntity capacitySourceObjectEntity) {
        this.capacitySourceObjectEntity = capacitySourceObjectEntity;
    }

    public double getWorkHours() {
        return workHours;
    }

    public void setWorkHours(double workHours) {
        this.workHours = workHours;
    }

    public double getValueOfPeriod() {
        return valueOfPeriod;
    }

    public void setValueOfPeriod(double valueOfPeriod) {
        this.valueOfPeriod = valueOfPeriod;
    }


}

