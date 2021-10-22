package net.codejava;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AppController {

    @Autowired
    private ProductService service;

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String viewHomePage(Model model) {
        return "index";
    }

    @PostMapping("/login")
    public ModelAndView viewLoginPage(@ModelAttribute("user") AppUser user) {
        AppUser user1 = userService.getUser(user.getName(), user.getPassword());
        if (Objects.isNull(user1)) {
            ModelAndView model = new ModelAndView("index");
            model.addObject("logError","logError");
            return  model;
        }

        ModelAndView mav = new ModelAndView("product");
        List<Product> listProducts = service.listAll();
        mav.addObject("listProducts", listProducts);
        if("admin".equalsIgnoreCase(user1.getRole())){
            mav.addObject("admin", "admin");
        }
        return mav;
    }

    @RequestMapping("/new")
    public String showNewProductPage(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);

        return "new_product";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveProduct(@ModelAttribute("product") Product product) {
        service.save(product);

        return "redirect:/";
    }

    @RequestMapping("/edit/{id}")
    public ModelAndView showEditProductPage(@PathVariable(name = "id") int id) {
        ModelAndView mav = new ModelAndView("edit_product");
        Product product = service.get(id);
        mav.addObject("product", product);

        return mav;
    }

    @RequestMapping("/delete/{id}")
    public String deleteProduct(@PathVariable(name = "id") int id) {
        service.delete(id);
        return "redirect:/";
    }
}
