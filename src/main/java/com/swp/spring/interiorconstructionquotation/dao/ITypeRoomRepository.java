package com.swp.spring.interiorconstructionquotation.dao;

import com.swp.spring.interiorconstructionquotation.entity.TypeRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestParam;

@RepositoryRestResource(path = "type-room")
public interface ITypeRoomRepository extends JpaRepository<TypeRoom, Integer> {
    Page<TypeRoom> findByRoomNameContaining(@RequestParam("name") String typeName, Pageable pageable);
    public TypeRoom findByRoomId(int roomId);
}
