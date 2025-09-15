package com.example.san.model.bussiness;


import com.example.san.enums.CoreStatusEnum;
import com.example.san.enums.IStatusEnum;
import com.example.san.model.baseModel.Srv;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.xml.bind.annotation.XmlTransient;
import java.beans.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import lombok.Data;


// Improved ActionResult class
@Data
public class ActionResult implements Serializable {

  private final boolean successful;

  private final String statusCode;

  @JsonIgnore
  private final IStatusEnum status;

  private final String description;

  private final Object detail;
  private final Long totalCount;

  public static final ActionResult SIMPLE_NECESSARY_FIELD = new ActionResult(
      CoreStatusEnum.UNFILLED_NECESSARY_FIELD);
  public static final ActionResult SIMPLE_FAILED = new ActionResult(CoreStatusEnum.FAILED);
  public static final ActionResult SIMPLE_DONE = new ActionResult(CoreStatusEnum.DONE);


  protected ActionResult() {
    this(CoreStatusEnum.DONE);
  }

  protected ActionResult(IStatusEnum statusEnum, Boolean success, Object detail, Long totalCount,
      String description) {
    super();

    this.status = statusEnum;
    this.statusCode = statusEnum.getPrefix() + statusEnum.getCode();

    this.successful = Objects.requireNonNullElseGet(success, statusEnum::isSuccessStatus);
    this.description = Objects.requireNonNullElseGet(description, statusEnum::getMessage);

    this.detail = detail;

    this.totalCount = totalCount;

  }

  protected ActionResult(String statusCode,  Boolean success, Object detail,
      Long totalCount, String description) {
    super();

    this.status = null;
    this.statusCode = statusCode;

    this.successful = success;
    this.description = description;

    this.detail = detail;

    this.totalCount = totalCount;
  }

  /**
   * Use when if only description is provided and process id failed
   * <p>response success set to <code>false</code> and status set to <code>CoreStatusEnum.FAILED</code></p>
   *
   * @param description the description of event/error
   */
/*    public ReturnValue(String description) {
        this(CoreStatusEnum.FAILED, description);
    }*/

  /**
   * Create Return value with specified status
   * <p>Successful filed and description filled by value inside statusEnum</p>
   *
   * @param statusEnum the status enum of response
   */
  public ActionResult(IStatusEnum statusEnum) {
    this(statusEnum, null, null);
  }

  public ActionResult(IStatusEnum statusEnum, String description) {
    this(statusEnum, null, null, null, description);
  }

  //list Detail without status
  public ActionResult(Collection<?> detail) {
    this(CoreStatusEnum.DONE, null, detail, null, null);
  }

  public ActionResult(Collection<?> detail, Long totalCount) {
    this(CoreStatusEnum.DONE, null, detail, totalCount, null);
  }

  public ActionResult(Collection<?> detail, Long totalCount, String description) {
    this(CoreStatusEnum.DONE, detail, totalCount, description);
  }

  public ActionResult(Collection<?> detail, String description) {
    this(CoreStatusEnum.DONE, null, detail, null, description);
  }


  //list Linkedhashmap
  public ActionResult(IStatusEnum statusEnum, Collection<? extends LinkedHashMap<?, ?>> detail) {
    this(statusEnum, null, detail, null, null);
  }


  /**
   * Create return value for returning error list
   *
   * @param detail String list of errors
   */
  public ActionResult(IStatusEnum statusEnum, List<String> detail) {
    this(statusEnum, null, detail, null, null);
  }

  //list detail by status
/*    public ReturnValue(IStatusEnum statusEnum, Collection<? extends BaseDto> detail) {
        this(statusEnum, null, detail, null, null);
    }*/

  public ActionResult(IStatusEnum statusEnum, Collection<?> detail, Long totalCount) {
    this(statusEnum, null, detail, totalCount, null);
  }


  public ActionResult(IStatusEnum statusEnum, Collection<?> detail, Long totalCount,
      String description) {
    this(statusEnum, null, detail, totalCount, description);
  }


  //one detail
  public ActionResult(Object detail) {
    this(CoreStatusEnum.DONE, detail);
  }

  public ActionResult(IStatusEnum statusEnum, Object detail) {
    this(statusEnum, null, detail, null, null);
  }

  public ActionResult(ActionResult actionResult) {

    this(actionResult.statusCode
        , actionResult.successful
        , actionResult.getDetail()
        , actionResult.totalCount
        , actionResult.description
    );


  }



/*
    public ReturnValue(Object detail) {
        this(CoreStatusEnum.DONE, null, detail, null, null);
    }
*/


  @SuppressWarnings("unchecked")
  @XmlTransient
  @Transient
  public <T> List<T> getDetailAsListOf(Class<T> clazz) {
    Object detail = getDetail();
    if (detail instanceof List) {
      return (List<T>) getDetail();
    } else if (detail instanceof Collection<?>) {
      return new ArrayList<T>((Collection<T>) detail);
    } else {
      List<T> list = new ArrayList<T>();
      list.add((T) detail);
      return list;
    }
  }

}
