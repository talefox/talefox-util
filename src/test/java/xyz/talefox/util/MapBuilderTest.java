package xyz.talefox.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("测试 MapBuilder 工具类")
class MapBuilderTest {
    @Test
    @DisplayName("返回构建的HashMap实例")
    void should_build_map() {
        Map<String, String> map = MapBuilder.<String, String>get()
                .put("a", "X")
                .put("b", "Y")
                .put("a", "Z")
                .build();
        assertEquals(2, map.size());
        assertEquals("Z", map.get("a"));
        assertEquals("Y", map.get("b"));
    }

    @Test
    @DisplayName("返回构建的指定类型的映射")
    void should_build_map_with_specific_type() {
        Map<Integer, String> map = MapBuilder.<Integer, String>get()
                .put(1, "x")
                .put(3, "y")
                .put(2, "z")
                .build(LinkedHashMap::new);
        assertEquals(3, map.size());
        Iterator<Map.Entry<Integer, String>> iterator = map.entrySet().iterator();
        Map.Entry<Integer, String> entry;
        assertTrue(iterator.hasNext());
        entry = iterator.next();
        assertEquals(1, entry.getKey());
        assertEquals("x", entry.getValue());
        assertTrue(iterator.hasNext());
        entry = iterator.next();
        assertEquals(3, entry.getKey());
        assertEquals("y", entry.getValue());
        assertTrue(iterator.hasNext());
        entry = iterator.next();
        assertEquals(2, entry.getKey());
        assertEquals("z", entry.getValue());
        assertFalse(iterator.hasNext());
    }
}
