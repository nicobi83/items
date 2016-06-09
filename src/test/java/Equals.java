import api.model.ItemTest;
import api.model.Items;

import java.io.Serializable;

/**
 * Created by nicob on 06/05/2016.
 */
public interface Equals {
    boolean equals(Object o);

    public class MyRunner extends Items implements Runnable, Equals, Serializable {
        public void run() {
            this.getItems();
        }
    }
}

