package self.map;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class AManager<T> {
    protected List<T> objects;

    public AManager() {
        this.objects = new ArrayList<>();
    }

    public void setObjects(List<T> objects) {
        this.objects = objects;
    }

    public void addObject(T object) {
        this.objects.add(object);
    }
}
