package CommonData;

import java.io.Serializable;

public class CommonData implements Serializable {
    public Object[] data;

    public CommonData(Object[] data) {
	this.data = data;
    }

    @Override
    public String toString() {
	return "cmd: " + data[0];
    }
}
