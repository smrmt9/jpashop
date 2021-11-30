package jpabook.jpashop.service;

import jpabook.jpashop.controller.BookForm;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;


    @Transactional
    public void saveItem(Item item){
        itemRepository.save(item);
    }

    @Transactional
    public void updateItem(Long itemId,UpdateItemDTO itemDTO){
        // 변경 감지에 따른 내용 변경
        // 해당 메소드가 끝나면 Spring Transactional에 의해 commit이 되고 flush를 한다 -> 그럼 바뀐 값을 자동으로 Update를 한다
        // Repository.save()를 할 필요가 없다
        // findItem -> 영속상태
        Item findItem = itemRepository.findOne(itemId);
        findItem.change(itemDTO.getName(), itemDTO.getPrice(), itemDTO.getStockQuantity());
    }

    public Item findItem(Long itemId){
        return itemRepository.findOne(itemId);
    }

    public List<Item> findItems(){
        return itemRepository.findAll();
    }

}
