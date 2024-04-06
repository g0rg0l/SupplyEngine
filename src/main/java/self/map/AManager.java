package self.map;

import java.util.ArrayList;
import java.util.List;

public class AManager<T> {
    protected List<T> objects;

    public AManager() {
        this.objects = new ArrayList<>();
    }

    public List<T> getObjects() {
        return objects;
    }

    public void setObjects(List<T> objects) {
        this.objects = objects;
    }

    public void addObject(T object) {
        this.objects.add(object);
    }
}
