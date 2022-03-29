package com.example.cw;

public class expenseModel {
    private int expense_id;

    public int getExpense_id() {
        return expense_id;
    }

    public void setExpense_id(int expense_id) {
        this.expense_id = expense_id;
    }

    public String getExpense() {
        return Expense;
    }

    public void setExpense(String expense) {
        Expense = expense;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public int getCar_ID() {
        return Car_ID;
    }

    public void setCar_ID(int car_ID) {
        Car_ID = car_ID;
    }

    public expenseModel(int expense_id,int car_ID, String expense, String note ) {
        this.expense_id = expense_id;
        Expense = expense;
        Note = note;
        Car_ID = car_ID;
    }

    public String Expense;
    public String Note;

    @Override
    public String toString() {
        return
                " £" + Car_ID +
                " Note: " + Expense ;
    }

    public int Car_ID;

}
