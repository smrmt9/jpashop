package jpabook.jpashop.controller;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private Logger logger = LoggerFactory.getLogger(Logger.class);
    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(Model model){
        model.addAttribute("bookForm", new BookForm());
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(@Valid BookForm form, BindingResult result){
        if(result.hasErrors()){
            return "items/createItemForm";
        }

        Book book = Book.createBook(form.getName(), form.getPrice(), form.getStockQuantity(), form.getIsbn(), form.getAuthor());
        itemService.setItem(book);
        logger.info(":::::::::::::::::::::::::::::::::::::::::::::::::::::::"+book.getId());
        return "redirect:/items";
    }

    @GetMapping("/items")
    public String list(Model model){
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items );
        return "items/itemList";
    }


    // @PathVariable : 주소에 들어간 해당 값을 사용한다
    @GetMapping("/items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model){
        Book book = (Book)itemService.findItem(itemId);
        BookForm bookForm = new BookForm(book.getId(), book.getName(), book.getPrice(), book.getStockQuantity(), book.getAuthor(), book.getIsbn());

        model.addAttribute("form", bookForm );

        return "items/updateItemForm";
    }


    @PostMapping("/items/{itemId}/edit")
    public String update(@PathVariable("itemId") Long itemId, @ModelAttribute("form") BookForm form, BindingResult result){
        if(result.hasErrors()){
            return "items/updateItemForm";
        }

        Book book = Book.builder().id(form.getId()).name(form.getName()).price(form.getPrice()).stockQuantity(form.getStockQuantity()).author(form.getAuthor()).isbn(form.getIsbn())
                .build();;

        itemService.setItem(book);

        return "redirect:/items";
    }


}
