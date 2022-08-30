package demo.library.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HttpResponse extends BaseEntity{
     /**
     * 0 for successs
     * 1 for no data found
     * -1 for failed
    */
    public Integer code; // status line of response
    public Object data; // data of request
    public String message; // annotate for resulut of request or give a notification
}