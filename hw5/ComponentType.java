/**
* Enum that defines the different Component Types.
* 
*
* @author Anubhav Bist
*    e-mail: anubhav.bist@stonybrook.edu
*    Stony Brook ID: 115877801
*    Recitation: R04
*/
public enum ComponentType {
    Button("Button"), Label("Label"), TextArea("TextArea"), HBox("HBox"), VBox("VBox"), AnchorPane("AnchorPane");
;
    public final String key;
    private ComponentType(String key){
        this.key = key;
    }
}
