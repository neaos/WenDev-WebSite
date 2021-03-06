package site.wendev.website.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.wendev.website.dao.TypeRepository;
import site.wendev.website.entities.Type;
import site.wendev.website.exception.NotFoundException;
import site.wendev.website.service.TypeService;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {
    @Autowired
    private TypeRepository typeRepository;

    @Override
    @Transactional
    public Type addType(Type type) {
        return typeRepository.save(type);
    }

    @Override
    public Type findByName(Type type) {
        return typeRepository.findByName(type.getName());
    }

    @Override
    @Transactional
    public Type getType(Long typeId) {
        return typeRepository.findById(typeId).orElse(null);
    }

    @Override
    @Transactional
    public Page<Type> listType(Pageable pageable) {
        return typeRepository.findAll(pageable);
    }

    @Override
    public List<Type> listType(Integer size) {
        Sort.Order order = new Sort.Order(Sort.Direction.DESC, "articles.size");
        Pageable pageable = PageRequest.of(0, size, Sort.by(order));
        return typeRepository.findTop(pageable);
    }

    @Override
    @Transactional
    public Type modify(Long typeId, String newName) {
        var typeOptional = typeRepository.findById(typeId);
        if (typeOptional.isEmpty()) {
            throw new NotFoundException("需要修改的标签不存在");
        } else {
            var type = typeOptional.get();
            type.setName(newName);
            return typeRepository.save(type);
        }
    }

    @Override
    @Transactional
    public void deleteType(Long typeId) {
        typeRepository.deleteById(typeId);
    }
}
