package ramzanzan.hraper.model;

import lombok.Data;

import java.net.URI;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@Data
public class Excerpts {
    private Map<String,List<String>> excerpts = new HashMap<>();
    private String origin;

    public void add(String name, String excerpt){
        if (!excerpts.containsKey(name))
            excerpts.put(name,new LinkedList<>());
        excerpts.get(name).add(excerpt);
    }

    public void add(String name, String excerpt, boolean asSingleton){
        if (asSingleton) {
            excerpts.put(name, Collections.singletonList(excerpt));
            return;
        }
        if (!excerpts.containsKey(name))
            excerpts.put(name,new LinkedList<>());
        excerpts.get(name).add(excerpt);
    }
}
