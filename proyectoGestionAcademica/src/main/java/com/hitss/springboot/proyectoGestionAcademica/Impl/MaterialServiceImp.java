package com.hitss.springboot.proyectoGestionAcademica.Impl;

import com.hitss.springboot.proyectoGestionAcademica.entities.Material;
import com.hitss.springboot.proyectoGestionAcademica.repositories.MaterialRepository;
import com.hitss.springboot.proyectoGestionAcademica.services.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MaterialServiceImp implements MaterialService {

    @Autowired
    private MaterialRepository materialRepository;

    @Override
    public List<Material> getAllMaterials() {
        return materialRepository.findAll();
    }

    @Override
    public Optional<Material> getMaterialById(Long id) {
        return materialRepository.findById(id);
    }

    @Override
    public Material createMaterial(Material material) {
        return materialRepository.save(material);
    }

    @Override
    public Material updateMaterial(Long id, Material updatedMaterial) {
        return materialRepository.findById(id).map(material -> {
            material.setTitle(updatedMaterial.getTitle());
            material.setDescription(updatedMaterial.getDescription());
            material.setUrl(updatedMaterial.getUrl());
            material.setAsignatura(updatedMaterial.getAsignatura());
            material.setTeachers(updatedMaterial.getTeachers());
            return materialRepository.save(material);
        }).orElseThrow(() -> new RuntimeException("Material no encontrado con id " + id));
    }

    @Override
    public void deleteMaterial(Long id) {
        if (!materialRepository.existsById(id)) {
            throw new RuntimeException("Material no encontrado con id " + id);
        }
        materialRepository.deleteById(id);
    }
}
