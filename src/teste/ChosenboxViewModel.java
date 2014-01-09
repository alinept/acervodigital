package teste;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
 

public class ChosenboxViewModel {
 
    private ListModelList<String> contactsModel = new ListModelList<String>(EmailContacts.getContacts());
    private ListModel<String> labelsModel = new ListModelList<String>(EmailLabels.getLabels());
 
    @Init
    public void init() {
    }
     
    @Command("newContact")
    public void newContact(@BindingParam("contact") String contact) {
        contactsModel.add(contact);
        contactsModel.addToSelection(contact);
    }
 
    public ListModel<String> getContactsModel() {
        return contactsModel;
    }
 
    public ListModel<String> getLabelsModel() {
        return labelsModel;
    }
}
