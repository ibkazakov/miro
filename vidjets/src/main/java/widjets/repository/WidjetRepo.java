package widjets.repository;

import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Component;

import java.util.*;


@Component
public class WidjetRepo {
    private Map<Long, Widjet> widjets = new HashMap<Long, Widjet>();
    private Long nextId = 0L;
    private Integer maxZ = 1;

    public synchronized Widjet createWidjet(Double x, Double y, Double width, Double height, Integer z) {
        if (isZcontains(z)) {
            shiftZ(z);
        }
        Widjet widjet = new Widjet(nextId, x, y, z, width, height);
        nextId++;
        widjets.put(widjet.getId(), widjet);
        return widjet;
    }

    // to the front. z will be maxZ
    public synchronized Widjet createWidjetWithoutZ(Double x, Double y, Double width, Double height) {
        Widjet widjet = new Widjet(nextId, x, y, maxZ, width, height);
        maxZ++;
        nextId++;
        widjets.put(widjet.getId(), widjet);

        return widjet;
    }

    public synchronized void deleteWidjet(Long id) {
        if (widjets.containsKey(id)) {
            widjets.remove(id);
        } else {
            System.out.println("Widject with id " + id +  " doesn't exist");
        }
    }

    public synchronized List<Widjet> getAllWidjets() {
        List<Widjet> allWidjets = new ArrayList<Widjet>(widjets.values());
        Collections.sort(allWidjets, new Comparator<Widjet>() {
            @Override
            public int compare(Widjet widjet, Widjet widjet1) {
                if (widjet.getZ() < widjet1.getZ()) {
                    return -1;
                } else {
                    if (widjet.getZ() > widjet1.getZ()) {
                        return 1;
                    } else {
                        return 0;
                    }
                }
            }
        });
        return allWidjets;
    }

    public synchronized Widjet getWidjetById(Long id) {
        if (widjets.containsKey(id)) {
            return widjets.get(id);
        } else {
            System.out.println("Widject with id " + id +  " doesn't exist");
            return null;
        }
    }

    public synchronized Widjet modifyWidjet(Long id, Double x, Double y, Double width, Double height, Integer z) {
            Widjet widjet = widjets.get(id);

            //no such widjets.repository
            if (widjet == null) {
                return null;
            }

            // null parameter means no change
            x = (x != null) ? x : widjet.getX();
            y = (y != null) ? y : widjet.getY();
            width = (width != null) ? width : widjet.getWidth();
            height = (height != null) ? height : widjet.getHeight();
            z = (z != null) ? z : widjet.getZ();

            // if z changes, we need to gaurantee uniqueness
            if (widjet.getZ() != z) {
                if (isZcontains(z)) {
                    shiftZ(z);
                }
            }
            widjet.changeParameters(x, y, z, width, height);
            return widjet;
    }

    public synchronized List<Widjet> getPageWidjets(Integer pageNumber, Integer pageSize) {
        List<Widjet> allWidjets = getAllWidjets();
        PagedListHolder<Widjet> pageHolder = new PagedListHolder<Widjet>(allWidjets);
        pageHolder.setPageSize(pageSize);
        pageHolder.setPage(pageNumber);
        return pageHolder.getPageList();
    }

    public synchronized List<Widjet> getAreaWidjets(Double left, Double top, Double right, Double bottom) {
        List<Widjet> areaWidjets = new LinkedList<Widjet>();
        for(Widjet widjet : getAllWidjets()) {
            if (isInArea(left, top, right, bottom, widjet)) {
                areaWidjets.add(widjet);
            }
        }
        return areaWidjets;
    }

    //only for tests
    public synchronized void refresh() {
        nextId = 0L;
        maxZ = 1;
        widjets.clear();
    }

    // +1 for all z that equal or bigger than z0
    private void shiftZ(int z) {
        for(Map.Entry<Long, Widjet> currentWidjetEntry : widjets.entrySet()) {
            Widjet currentWidjet = currentWidjetEntry.getValue();
            if (currentWidjet.getZ() >= z) {
                currentWidjet.incrementZ();
            }
        }
        maxZ++;
    }

    private boolean isZcontains(int z) {
        for(Map.Entry<Long, Widjet> currentWidjetEntry : widjets.entrySet()) {
            Widjet currentWidjet = currentWidjetEntry.getValue();
            if (currentWidjet.getZ() == z) {
                return true;
            }
        }
        return false;
    }

    // (x,y) is the center of the widjets.repository
    private static boolean isInArea(Double left, Double top, Double right, Double bottom, Widjet widjet) {
        boolean leftOverlap = (widjet.getX() - widjet.getWidth()/2 < left);
        boolean rightOverlap = (widjet.getX() + widjet.getWidth()/2 > right);
        boolean topOverlap = (widjet.getY() - widjet.getHeight()/2 < top);
        boolean bottomOverlap = (widjet.getY() + widjet.getHeight()/2 > bottom);
        return !(leftOverlap || rightOverlap || topOverlap || bottomOverlap);
    }
}
