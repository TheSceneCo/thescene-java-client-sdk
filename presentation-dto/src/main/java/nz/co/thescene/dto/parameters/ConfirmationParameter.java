package nz.co.thescene.dto.parameters;


public enum ConfirmationParameter {

    YES("yes"), NO("no");

    private String text;

    public String getText() {
        return text;
    }

    ConfirmationParameter(String text){
        this.text = text;
    }

    @Override
    public String toString(){
        return this.name().toLowerCase();
    }
}
