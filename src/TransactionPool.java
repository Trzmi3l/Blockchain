import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TransactionPool<Transaction> extends ArrayList<Transaction> {

    // Interfejs słuchacza dla zdarzenia dodania elementu
    public interface ElementAddedListener<E> {
        void onElementAdded(E element);
    }

    // Lista słuchaczy
    private List<ElementAddedListener<Transaction>> elementAddedListeners = new ArrayList<>();

    @Override
    public boolean add(Transaction element) {
        boolean result = super.add(element);

        // Wywołaj zdarzenie dodania elementu
        fireElementAddedEvent(element);

        return result;
    }

    public Transaction lastItem() {
        if(super.isEmpty()) return null;
        return super.get(super.size());
    }
    public List<Transaction> TakeAll() {
        List<Transaction> temp = new ArrayList<>();
        Iterator<Transaction> iter = super.iterator();
        while(iter.hasNext()) {
            temp.add(iter.next());
        }
        return temp;
    }

    public List<Transaction> TakeAndPurge() {
        List<Transaction> temp = new ArrayList<>();
        Iterator<Transaction> iter = super.iterator();
        while(iter.hasNext()) {
            temp.add(iter.next());
        }
        super.clear();
        return temp;
    }

    // Dodaj słuchacza zdarzenia dodania elementu
    public void addElementAddedListener(ElementAddedListener<Transaction> listener) {
        elementAddedListeners.add(listener);
    }

    // Usuń słuchacza zdarzenia dodania elementu
    public void removeElementAddedListener(ElementAddedListener<Transaction> listener) {
        elementAddedListeners.remove(listener);
    }

    // Wywołaj zdarzenie dodania elementu dla wszystkich słuchaczy
    private void fireElementAddedEvent(Transaction element) {
        for (ElementAddedListener<Transaction> listener : elementAddedListeners) {
            listener.onElementAdded(element);
        }
    }

    // Inne metody obsługujące operacje na liście (np. remove, clear) można dodać w podobny sposób.
}
