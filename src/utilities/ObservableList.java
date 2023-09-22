package utilities;
import java.util.ArrayList;
import java.util.List;

public class ObservableList<E> extends ArrayList<E> {

    // Interfejs słuchacza dla zdarzenia dodania elementu
    public interface ElementAddedListener<E> {
        void onElementAdded(E element);
    }

    // Lista słuchaczy
    private List<ElementAddedListener<E>> elementAddedListeners = new ArrayList<>();

    @Override
    public boolean add(E element) {
        boolean result = super.add(element);

        // Wywołaj zdarzenie dodania elementu
        fireElementAddedEvent(element);

        return result;
    }

    public E lastItem() {
        return super.get(super.size()-1);
    }
    public E prevItem() {
        if(super.size() > 1) {
            return lastItem();
        } else {
            return super.get(super.size()-2);
        }
    }

    // Dodaj słuchacza zdarzenia dodania elementu
    public void addElementAddedListener(ElementAddedListener<E> listener) {
        elementAddedListeners.add(listener);
    }

    // Usuń słuchacza zdarzenia dodania elementu
    public void removeElementAddedListener(ElementAddedListener<E> listener) {
        elementAddedListeners.remove(listener);
    }

    // Wywołaj zdarzenie dodania elementu dla wszystkich słuchaczy
    private void fireElementAddedEvent(E element) {
        for (ElementAddedListener<E> listener : elementAddedListeners) {
            listener.onElementAdded(element);
        }
    }

    // Inne metody obsługujące operacje na liście (np. remove, clear) można dodać w podobny sposób.
}
