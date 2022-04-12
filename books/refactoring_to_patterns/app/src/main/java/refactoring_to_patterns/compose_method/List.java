package refactoring_to_patterns.compose_method;

public class List {

    private Object[] elements;
    private int newSize;
    private int size;
    private boolean readonly;

    public void add(Object element) {
        if(readonly) {
            return;
        }

        newSize = size + 1;
        if (newSize > elements.length) {
            Object[] newElements = new Object[elements.length + 10];
            for (int i = 0; i < size; i++) {
                newElements[i] = elements[i];
            }
            elements = newElements;
        }
        elements[size++] = element;
    }
}
