import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Locale;

/**
 * Класс используется для хранения информации о точке
 * @author maria
 */

@Getter
@Setter
@Entity
@Table(name="points")
public class Point implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private float x = 0.0f;
    @Column
    private float y = 0.0f;
    @Column
    private float r = 2.0f;
    @Column
    private boolean status;
    @Column
    private String time;
    @Column
    private long scriptTime;

    public String getStatus() {
        if (status) return "Hit!";
        else return "Miss!";
    }

    public String toJSON() {
        return String.format(Locale.US, """
                {
                "x": %.3f,
                "y": %.3f,
                "r": %.3f,
                "status": "%s",
                "time": "%s",
                "scriptTime": %d
                }
                """, x, y, r, getStatus(), time, scriptTime);
    }
}
