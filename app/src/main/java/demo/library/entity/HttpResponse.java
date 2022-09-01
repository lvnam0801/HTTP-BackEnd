package demo.library.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class HttpResponse extends BaseEntity{
     /**
     * 0 for successs
     * 1 for no data found
     * -1 for failed
    */
    private Integer code; // status line of response
    private Object data; // data of request
    private String message; // annotate for resulut of request or give a notification
}