package com.example.san.Model.Bussiness;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActionResult {


    private List<Object> data = new ArrayList<>();
    private int code;
    private String Message;


    public ActionResult(Object data, int code, String message) {
        List<Object> objects = new ArrayList<>();
        objects.add(data);
        this.data = objects;
        this.code = code;
        Message = message;
    }

    public ActionResult(int code, String message) {
        this.code = code;
        Message = message;
    }


}
