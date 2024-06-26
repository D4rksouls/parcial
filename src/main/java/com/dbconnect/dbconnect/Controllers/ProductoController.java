package com.dbconnect.dbconnect.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.dbconnect.dbconnect.Models.DAO.IProductoDao;
import com.dbconnect.dbconnect.Models.Entity.Producto;

import jakarta.validation.Valid;

@Controller
public class ProductoController {
    @Autowired
    private IProductoDao IProductoDao;

    @GetMapping("/producto/listar")
    public String Listar(Model model) {
        model.addAttribute("title", "Listado de Productos");
        model.addAttribute("producto", IProductoDao.findAll());
        return "/producto/listar";
    }

    @GetMapping("/producto/form")
    public String create(Model model) {
        Producto producto = new Producto();
        model.addAttribute("title", "Nuevo Producto");
        model.addAttribute("producto", producto);
        return "/producto/form";
    }

    @PostMapping("/producto/form")
    public String saveClient(@Valid Producto producto, BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("title", "Nuevo producto");
            return "/producto/form";
        }
        IProductoDao.saveProducto(producto);

        return "redirect:/producto/listar";
    }

    @GetMapping("/producto/eliminar/{id}")
    public String delete(@PathVariable String id) {
        Long idLong = Long.parseLong(id);
        IProductoDao.delete(idLong);
        return "redirect:/producto/listar";
    }

    @GetMapping("/producto/editar/{param}")
    public String edit(@PathVariable String param, Model model) {
        Long id = Long.parseLong(param);

        model.addAttribute("title", "Editar producto");
        model.addAttribute("producto", IProductoDao.findById(id));

        return "/producto/formEditar";
    }

    @PostMapping("/producto/editar")
    public String edit(Producto producto) {
        IProductoDao.edit(producto);

        return "redirect:/producto/listar";
    }

}
