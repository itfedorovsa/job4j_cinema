package ru.job4j.cinema.model;

import java.util.Objects;

/**
 * Seat data model
 *
 * @author itfedorovsa (itfedorovsa@gmail.com)
 * @version 1.0
 * @since 03.11.22
 */
public class Seat {

    /**
     * Seat id (id from SeatGridService grid). Used to get row and cell values from SeatGridService.
     */
    int seatId;

    /**
     * Row number
     */
    int row;

    /**
     * Cell number
     */
    int cell;

    public Seat() {
    }

    public Seat(int seatId) {
        this.seatId = seatId;
    }

    public Seat(int row, int cell) {
        this.row = row;
        this.cell = cell;
    }

    public Seat(int seatId, int row, int cell) {
        this.seatId = seatId;
        this.row = row;
        this.cell = cell;
    }

    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCell() {
        return cell;
    }

    public void setCell(int cell) {
        this.cell = cell;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Seat seat = (Seat) o;
        return seatId == seat.seatId && row == seat.row && cell == seat.cell;
    }

    @Override
    public int hashCode() {
        return Objects.hash(seatId, row, cell);
    }

    @Override
    public String toString() {
        return "Row " + row + ", Cell " + cell;
    }

}
