package kernel;

import java.util.ArrayList;

public class MapForUsersFrequency {
    int id;
    int count;

    void MapForUsersFrequency(int id) {
        this.id = id;
        this.count = 0;
    }

    void iterateCount() {
        this.count++;
    }
}
