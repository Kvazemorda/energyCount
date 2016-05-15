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
import vankor.EnergyDepartment.WriteDataUnitCountToJournal.JournalFillEntity;

/**
 * Создает формы с мощностью = 0;
 * В поле ввода указывается итоговый объем потребленный за период
 */
public class ValueResourceZeroCapacity {
    private CapacitySourceObjectEntity capacitySourceObjectEntity;
    private TextField tfValueFilled;
    public double valueOfPeriod;
    private Label labelValueOfPeriod, labelDescription;
    public boolean source = false;
    private JournalFillEntity journalFillEntity;
    private String background = "-fx-background-color: #abf7c4";

    public ValueResourceZeroCapacity(CapacitySourceObjectEntity capacitySourceObjectEntity) {
        this.capacitySourceObjectEntity = capacitySourceObjectEntity;
    }

    public VBox createForm(){
        tfValueFilled = new TextField("0");
        tfValueFilled.setMaxWidth(45);
        tfValueFilled.setAlignment(Pos.TOP_CENTER);
        tfValueFilled.setStyle("-fx-padding: 4px,4px;");
        labelDescription = new Label(capacitySourceObjectEntity.getContractEntity().toString());
        labelDescription.setStyle("-fx-padding: 5");
        labelValueOfPeriod = new Label("Объем ");
        labelValueOfPeriod.setStyle("-fx-padding: 4px,4px;");
        valueOfPeriod = 0;
        journalFillEntity = new JournalFillEntity(MainForm.currentDate, valueOfPeriod, capacitySourceObjectEntity);
        HBox hBox = new HBox(labelValueOfPeriod, tfValueFilled);
        hBox.setSpacing(5);
        VBox vBox = new VBox(labelDescription, hBox);
        vBox.setStyle("-fx-padding: 6;" + background);
        vBox.setSpacing(5);
        changeValueFilled();
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

    public void changeValueFilled(){
        try {
            valueOfPeriod = Double.parseDouble(tfValueFilled.getText());
            if (valueOfPeriod < 0) {
                throw new HoursWorkException();
            }else{
                tfValueFilled.focusedProperty().addListener((observable, oldValue, newValue) -> {
                        changeValueToBalance();
                        valueOfPeriod = Double.parseDouble(tfValueFilled.getText());
                        addValueToBalance();
                        ValueResourceWithUnitCount.balanceResource = ValueResourceWithUnitCount.countValueSource - ValueResourceWithUnitCount.countValueConsumer;
                        JournalAddNewCountOrValue.labelBalanceResource.setText(String.valueOf(ValueResourceWithUnitCount.balanceResource));
                        journalFillEntity.setValue(valueOfPeriod);
                        journalFillEntity.setDate(MainForm.currentDate);

                });
            }
        }catch (HoursWorkException e) {
            String text = "Объем должен быть больше или равен 0";
            DialogWindow dialogWindow = new DialogWindow(text);
            tfValueFilled.setText("0");
        }catch (NumberFormatException e){
            e.printStackTrace();
            String text = "Объем должен быть числом";
            DialogWindow dialogWindow = new DialogWindow(text);
            tfValueFilled.setText("0");
        }

    }

    public CapacitySourceObjectEntity getCapacitySourceObjectEntity() {
        return capacitySourceObjectEntity;
    }

    public void setCapacitySourceObjectEntity(CapacitySourceObjectEntity capacitySourceObjectEntity) {
        this.capacitySourceObjectEntity = capacitySourceObjectEntity;
    }

    public JournalFillEntity getJournalFillEntity() {
        return journalFillEntity;
    }

    public void setJournalFillEntity(JournalFillEntity journalFillEntity) {
        this.journalFillEntity = journalFillEntity;
    }
}

