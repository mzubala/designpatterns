package pl.com.bottega.designpatterns.user;

import java.util.ArrayList;
import java.util.Collection;

public class InvoiceCorrectorRole extends UserRole {

    private Collection<String> correctedInvoices = new ArrayList<>();

    public void invoiceCorrected(String nr) {
        correctedInvoices.add(nr);
    }

}
