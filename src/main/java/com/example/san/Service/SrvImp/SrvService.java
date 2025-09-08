package com.example.san.Service.SrvImp;

import com.example.san.Model.BaseModel.SanService;
import com.example.san.Model.Bussiness.ActionResult;
import com.example.san.Service.ISrvService;
import com.example.san.repository.ServiceRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SrvService implements ISrvService {

  @Autowired
  private ServiceRepository iServiceRepository;

  @Override
  public ActionResult save(String name, long cost, long capa, LocalDateTime startTime) {
    try {
      LocalDateTime endTime = this.setServiceDuring(startTime);
      SanService sanService = new SanService(cost, capa, name, startTime, endTime);

      return new ActionResult(iServiceRepository.save(sanService));
    } catch (Exception e) {

      return ActionResult.SIMPLE_FAILED;

    }
  }

  @Override
  public ActionResult remove(long ServiceId) {

    try {
      SanService sanService = iServiceRepository.getById(ServiceId);
      sanService.setActive(false);
      sanService.setDeleteDateAndTime(LocalDateTime.now());
      iServiceRepository.save(sanService);
      return ActionResult.SIMPLE_DONE;
    } catch (Exception e) {

      return ActionResult.SIMPLE_FAILED;

    }

  }

  @Override
  public ActionResult edit(long serviceId, String name, long cost, long capa,
      LocalDateTime startTime) {
    try {
      SanService oldSanService = iServiceRepository.getById(serviceId);
      if (name != null) {
        oldSanService.setName(name);
      }
      if (capa != 0) {
        oldSanService.setCapacity(capa);
      }
      if (cost != 0) {
        oldSanService.setCost(cost);
      }
      if (startTime != null) {
        oldSanService.setStartTime(startTime);
      }

      oldSanService.setLastUpdateDateAndTime(LocalDateTime.now());

      SanService newSanService = iServiceRepository.save(oldSanService);
      return new ActionResult(newSanService);
    } catch (Exception e) {

      return ActionResult.SIMPLE_FAILED;

    }


  }

  @Override
  public ActionResult getAll() {

    try {
      return new ActionResult(iServiceRepository.findAll());

    } catch (Exception e) {

      return ActionResult.SIMPLE_FAILED;

    }
  }


  @Override
  public ActionResult getAllActiveServices() {
    try {
      return new ActionResult(
          iServiceRepository.findAllByActive(true));

    } catch (Exception e) {

      return ActionResult.SIMPLE_FAILED;

    }
  }

  @Override
  public ActionResult getAllRelatedServices(Long userId) {
    try {

      List<SanService> sanServices = iServiceRepository.getAllByUserServices_user_id(userId);

      return new ActionResult(sanServices);
    } catch (Exception e) {

      return ActionResult.SIMPLE_FAILED;

    }
  }

  @Override
  public List<SanService> getByName(String name) {
    return iServiceRepository.findByName(name);
  }

  @Override
  public ActionResult activateService(long serviceId) {

    try {
      SanService sanService = (SanService) iServiceRepository.getById(serviceId);
      if (sanService != null) {
        sanService.setActive(true);
      }

      return new ActionResult(iServiceRepository.save(sanService));
    } catch (Exception e) {

      return ActionResult.SIMPLE_FAILED;
    }
  }

  @Override
  public ActionResult deactivateService(long serviceId) {
    try {
      SanService sanService = (SanService) iServiceRepository.getById(serviceId);
      sanService.setActive(false);
      return new ActionResult(iServiceRepository.save(sanService));
    } catch (Exception e) {

      return ActionResult.SIMPLE_FAILED;

    }

  }

  private LocalDateTime setServiceDuring(LocalDateTime startTime) {
    return startTime.plusHours(12);

  }

}
