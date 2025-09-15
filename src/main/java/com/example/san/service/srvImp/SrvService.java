package com.example.san.service.srvImp;

import com.example.san.enums.UserStatus;
import com.example.san.exception.UserException;
import com.example.san.model.baseModel.Srv;
import com.example.san.repository.SrvRepository;
import com.example.san.service.ISrvService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SrvService implements ISrvService {

  @Autowired
  private SrvRepository iSrvRepository;

  @CachePut(value = "services", key = "#result.id")
  @Override
  public Srv save(String name, long cost, long capa, LocalDateTime startTime) {
    LocalDateTime endTime = this.setServiceDuring(startTime);
    Srv srv = new Srv(cost, capa, name, startTime, endTime);

    return iSrvRepository.save(srv);

  }

  @CacheEvict(value = "services", key = "#serviceId")
  @Override
  public Srv remove(long serviceId) {

    Optional<Srv> srvOptional = iSrvRepository.findById(serviceId);
    if (srvOptional.isEmpty()) {
      throw new UserException(UserStatus.USER_NOT_FOUND);
    }
    Srv srv = srvOptional.get();
    srv.setActive(false);
    srv.setDeleteDateAndTime(LocalDateTime.now());
    return iSrvRepository.save(srv);


  }

  @CachePut(value = "services", key = "#serviceId")
  @Override
  @Transactional
  public Srv edit(Long serviceId, String name, Long cost, Long capacity, LocalDateTime startTime) {

    Srv oldSrv = iSrvRepository.findById(serviceId)
        .orElseThrow(() -> new UserException(UserStatus.USER_NOT_FOUND));

    Optional.ofNullable(name).ifPresent(oldSrv::setName);
    Optional.ofNullable(cost).ifPresent(oldSrv::setCost);
    Optional.ofNullable(capacity).ifPresent(oldSrv::setCapacity);
    Optional.ofNullable(startTime).ifPresent(oldSrv::setStartTime);

    oldSrv.setLastUpdateDateAndTime(LocalDateTime.now());

    return iSrvRepository.save(oldSrv);

  }


  @Override
  public List<Srv> getAll() {
    return iSrvRepository.findAll();

  }


  @Override
  public List<Srv> getAllActiveServices() {

    return iSrvRepository.findAllByActive(true);


  }

  @Override
  public List<Srv> getAllRelatedServices(Long userId) {

    return iSrvRepository.getAllByUserServices_user_id(userId);
  }

  @Override
  public List<Srv> getByName(String name) {
    return iSrvRepository.findByName(name);
  }

  @CachePut(value = "services", key = "#serviceId")
  @Override
  public Srv activateService(long serviceId) {

    Optional<Srv> srvOptional = iSrvRepository.findById(serviceId);
    if (srvOptional.isEmpty()) {
      throw new UserException(UserStatus.USER_NOT_FOUND);
    }
    Srv srv = srvOptional.get();
    srv.setActive(true);

    return iSrvRepository.save(srv);

  }

  @CachePut(value = "services", key = "#serviceId")
  @Override
  public Srv deactivateService(long serviceId) {
    Optional<Srv> srvOptional = iSrvRepository.findById(serviceId);
    if (srvOptional.isEmpty()) {
      throw new UserException(UserStatus.USER_NOT_FOUND);
    }
    Srv srv = srvOptional.get();
    srv.setActive(false);
    return iSrvRepository.save(srv);


  }

  @Cacheable(value = "services", key = "#id")
  @Override
  public Srv getById(Long id) {
    Optional<Srv> srvOptional = iSrvRepository.findById(id);

    if (srvOptional.isEmpty()) {
      throw new UserException(UserStatus.USER_NOT_FOUND);
    }

    return srvOptional.get();
  }

  private LocalDateTime setServiceDuring(LocalDateTime startTime) {
    return startTime.plusHours(12);

  }

}
