package widjets.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import widjets.repository.Widjet;
import widjets.repository.WidjetRepo;

import java.util.List;

@RestController
public class RestAPI {

    @Autowired
    private WidjetRepo widjetRepo;


    @RequestMapping("/add")
    public Widjet add(@RequestParam("x") Double x, @RequestParam("y") Double y,
                      @RequestParam("width") Double width, @RequestParam("height") Double height,
                      @RequestParam(value = "z", required = false) Integer z) {
        if (z == null) {
            return widjetRepo.createWidjetWithoutZ(x, y, width, height);
        } else {
            return widjetRepo.createWidjet(x, y, width, height, z);
        }
    }

    @RequestMapping("/all")
    public List<Widjet> allWidjets() {
        return widjetRepo.getAllWidjets();
    }

    @RequestMapping("/widjetID")
    public Widjet getWidjetById(@RequestParam("id") Long id) {
        return widjetRepo.getWidjetById(id);
    }

    @RequestMapping("/delete")
    public void deleteWidjet(@RequestParam("id") Long id) {
        widjetRepo.deleteWidjet(id);
    }

    @RequestMapping("/modify")
    public Widjet modifyWidjet(@RequestParam("id") Long id,
                             @RequestParam(value = "x", required = false) Double x,
                             @RequestParam(value = "y", required = false) Double y,
                             @RequestParam(value = "width", required = false) Double width,
                             @RequestParam(value = "height", required = false) Double height,
                             @RequestParam(value = "z", required = false) Integer z) {

        return widjetRepo.modifyWidjet(id, x, y, width, height, z);
    }


    @RequestMapping("/widjectPage")
    public List<Widjet> pageWidjects(@RequestParam("page") Integer pageNumber,
                                     @RequestParam(value = "size", required = false, defaultValue = "10") Integer pageSize) {

        // check and correcting pageSize
        if (pageSize <= 0) {
            pageSize = 10;
        }
        if (pageSize >= 500) {
            pageSize = 500;
        }

        return widjetRepo.getPageWidjets(pageNumber, pageSize);
    }

    @RequestMapping("/areaWidjets")
    public List<Widjet> areaWidjets(@RequestParam("left") Double left,
                                    @RequestParam("right") Double right,
                                    @RequestParam("top") Double top,
                                    @RequestParam("bottom") Double bottom) {

        return widjetRepo.getAreaWidjets(left, top, right, bottom);

    }
}
