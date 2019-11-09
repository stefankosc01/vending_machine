package vending;

public enum Coin {
    JEDENGROSZ(1),
    DWAGROSZE(2),
    PIECGROSZY(5),
    DZIESIECGROSZY(10),
    DWADZIESCIAGROSZY(20),
    PIECDZIESIATGROSZY(50),
    JEDENZLOTY(100),
    DWAZLOTE(200),
    PIECZLOTYCH(500);

    private int denomination;
    private Coin(int denomination){

        this.denomination = denomination;
    }
    public int getDenomination(){

        return denomination;
    }
}

