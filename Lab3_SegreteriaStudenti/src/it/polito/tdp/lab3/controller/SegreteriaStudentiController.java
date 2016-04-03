package it.polito.tdp.lab3.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.lab3.model.Corso;
import it.polito.tdp.lab3.model.SegreteriaStudentiModel;
import it.polito.tdp.lab3.model.Studente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class SegreteriaStudentiController {
	
	//model contiene il riferimento al modello su cui opera il Controller
	private SegreteriaStudentiModel model;
	
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> boxCorsi;

    @FXML
    private TextField txtMatricola;

    @FXML
    private ImageView imImage;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtCognome;

    @FXML
    private Button btnCerca;

    @FXML
    private Button btnIscrivi;

    @FXML
    private TextArea txtResult;

    @FXML
    private Button btnReset;
    
	//setto il modello su cui opera il Controller
	public void setModel(SegreteriaStudentiModel model){
		this.model=model;
		//ora che conosco il modello, posso inizializzare la lista della ComboBox
        boxCorsi.getItems().addAll(model.popolaTendina());
        /*OSS:non potevo fare ciò nel metodo initialize() perchè esso viene
         * chiamato subito dopo il costruttore, prima di setModel, perciò 
         * quando il MODEL non è stato ancora associato al CONTROLLER!!!
         * ALMENO CREDO, CHIEDERE SE è VERO O SE SI POTEVA FARE ANCHE Lì
         */
	}

    @FXML
    void doCerca(ActionEvent event) {
    	
    	/*se premo reset la combo vale null, però posso anche aver selezionato
    	  il campo vuoto "" presente nell'elenco
    	 */
    	if((boxCorsi.getValue()==null || boxCorsi.getValue().equals("")) && txtMatricola.getText().equals("")){
    		txtResult.setText("SELEZIONARE UN CORSO E\\O INSERIRE UNA MATRICOLA");
    	}
    	else{
    		if(txtMatricola.getText().equals("")){
    			//ELENCO STUDENTI ISCRITTI AD UN DATO CORSO 
        		
    			List<Studente> lista=null;
    			if(model.iscrittiAlCorso(boxCorsi.getValue())==null)
    			    txtResult.setText("NESSUNO STUDENTE ISCRITTO AL CORSO");
    			else
    			{
    				lista = model.iscrittiAlCorso(boxCorsi.getValue());
    			
    				String res="";
    				for(Studente el:lista){
    					res+=el+"\n";
    				}
        		txtResult.setText(res);
    			}
    			return;
    		}
    		if(boxCorsi.getValue()==null || boxCorsi.getValue().equals("")){
    			//ELENCO CORSI A CUI E' ISCRITTO UN DATO STUDENTE
    	    	Studente s=model.searchStudent(txtMatricola.getText());
    	    	if(s==null){
    	    		txtResult.setText("ERRORE: Studente assente");
    	    	}else{
    	    		if(model.corsiDelloStudente(s)==null)
    	    			txtResult.setText("NESSUN CORSO SEGUITO");
    	    		else{
    	    			String res="";
    	    			for(Corso el:model.corsiDelloStudente(s)){
    						res+=el.toString()+"\n";    			
    					}
    	    			txtResult.setText(res);
    	    		}
    	    	}
    			    			
    			return;
    		}
    		/*se sono qui vuol dire che ho sia selezionato il corso che 
    		 * inserito la matricola
    		 */
    		//CHECK SE UN DATO STUDENTE è ISCRITTO AD UN DATO CORSO
    		Studente s=model.searchStudent(txtMatricola.getText());
    		Corso c=model.searchCourse(boxCorsi.getValue());
	    	if(s==null || c==null){
	    		txtResult.setText("ERRORE: Studente o Corso assente");
	    	}else{
	    		if(model.matchStudentCourse(s, c))
	    			txtResult.setText(String.format("%s %s (%s) e' iscritto a \"%s\"", s.getNome(),s.getCognome(),s.getMatricola(),c.getNomeCorso()));
	    		else
	    			txtResult.setText(String.format("%s %s (%s) non e' iscritto a \"%s\"", s.getNome(),s.getCognome(),s.getMatricola(),c.getNomeCorso()));
	    		}		
    		}    		

    }

    @FXML
    void doClickImage(MouseEvent event) {
    	String matr=txtMatricola.getText();
    	if(matr.equals("")){
    		txtResult.setText("ERRORE: Inserisci la matricola");
    		return;
    	}
    		
    	Studente s=model.searchStudent(matr);
    	if(s==null){
    		txtResult.setText("ERRORE: Studente assente");
    		return;
    	}
    	txtNome.setText(s.getNome());;
    	txtCognome.setText(s.getCognome());

    }

    @FXML
    void doIscrivi(ActionEvent event) {
    	if(boxCorsi.getValue()==null || boxCorsi.getValue().equals("") || txtMatricola.getText().equals("")){
    		txtResult.setText("SELEZIONARE UN CORSO E\\O INSERIRE UNA MATRICOLA");
    		return;
    	}
    	Studente s=model.searchStudent(txtMatricola.getText());
		Corso c=model.searchCourse(boxCorsi.getValue());
    	if(s==null || c==null){
    		txtResult.setText("ERRORE: Studente o Corso assente");
    		return;
    	}
        if(model.matchStudentCourse(s, c)){
        	txtResult.setText("STUDENTE GIA' ISCRITTO A TALE CORSO");
        	return;
        }
        
        if(model.iscrizione(s, c))
        	txtResult.setText(String.format("%s %s (%s) e' stato iscritto a \"%s\"", s.getNome(),s.getCognome(),s.getMatricola(),c.getNomeCorso()));	
        else
        	txtResult.setText(String.format("ERRORE: %s %s (%s) non e' stato iscritto a \"%s\"", s.getNome(),s.getCognome(),s.getMatricola(),c.getNomeCorso()));
    }

    @FXML
    void doReset(ActionEvent event) {
    	txtNome.clear();
    	txtCognome.clear();
    	txtMatricola.clear();
    	txtResult.clear();
    	boxCorsi.setValue(null);

    }

    @FXML
    void initialize() {
        assert boxCorsi != null : "fx:id=\"boxCorsi\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert txtMatricola != null : "fx:id=\"txtMatricola\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert imImage != null : "fx:id=\"imImage\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert txtNome != null : "fx:id=\"txtNome\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert txtCognome != null : "fx:id=\"txtCognome\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert btnCerca != null : "fx:id=\"btnCerca\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert btnIscrivi != null : "fx:id=\"btnIscrivi\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert btnReset != null : "fx:id=\"btnReset\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";

        
        
    }
}

