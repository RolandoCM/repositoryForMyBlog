package com.org.coop.retail.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.org.coop.retail.entities.MaterialMaster;

public interface RetailMaterialMasterRepository extends JpaRepository<MaterialMaster, Integer> {
	@Query("select mm from MaterialMaster mm order by mm.materialGroup.materialGrpId, mm.createDate desc")
	public List<MaterialMaster> findAllMaterials();
}
