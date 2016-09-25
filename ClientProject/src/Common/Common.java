package Common;

import java.io.Serializable;

public class Common implements Serializable {
    public Object[] data;
    
    public Common(Object[] data) {
        this.data = data;
    }
    
    @Override
    public String toString() {
	return "cmd: " + data[0];
    }
}
