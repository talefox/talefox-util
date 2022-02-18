package xyz.talefox.util;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

/**
 * 为对象提供延迟加载程序。
 * <p>基于双检锁（Double Check Lock，DCL）实现。</p>
 *
 * @param <T> 表示对象的类型。
 * @author 梁济时
 * @since 2021/9/23
 */
public class LazyLoader<T> implements Supplier<T> {
    private final Supplier<T> loader;
    private final Lock lock;
    private volatile T instance;

    /**
     * 使用用以获取对象实例的方法初始化 {@link LazyLoader} 类的新实例。
     *
     * @param loader 表示用以获取对象实例的方法的 {@link Supplier}。
     * @throws IllegalArgumentException {@code supplier} 为 {@code null}。
     */
    public LazyLoader(Supplier<T> loader) {
        this.loader = Validation.notNull(loader, "The loader to load singleton cannot be null.");
        this.lock = new ReentrantLock();
    }

    @Override
    public T get() {
        if (this.instance == null) {
            this.lock.lock();
            try {
                if (this.instance == null) {
                    this.instance = this.loader.get();
                }
            } finally {
                this.lock.unlock();
            }
        }
        return this.instance;
    }
}
