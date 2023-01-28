package com.study.userservice;

import com.study.userservice.controller.UserServiceController;
import com.study.userservice.feign.UserOrderFeign;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.cglib.proxy.Proxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class ApplicationTests {

    /**
     *  问号表示通配符,可以接收任意类型
     *  问号 继承 Number 表示最高上限到Number, Number的所有子类都可以接收
     * @param box
     */
    public static void showBox(Box<? extends Number> box) {
        Number first = box.getFirst();
        System.out.println(first);
    }


    /**
     * 泛型上限通配符，传递的集合类型只能是Cat或Cat的子类类型
     * @param list
     */
    public static void upperLimitShowAnimal(ArrayList<? extends Cat> list) {
        for (int i = 0; i < list.size(); i++) {
            Cat cat = list.get(i);
            System.out.println(cat);
        }
    }


    /**
     * 泛型下限通配符，要求集合只能是Cat或Cat的父类类型
     * @param list
     */
    public static void lowerLimitShowAnimal(List<? super Cat> list) {
        for (Object o : list) {
            System.out.println(o);
        }
    }

    @Test
    void productGetter() {
        ProductGetter<String> product = new ProductGetter<>();
        String[] strProduct = {"苹果手机", "华为手机", "小米手机"};

        for (int i = 0; i < strProduct.length; i++) {
            product.addProduct(strProduct[i]);
        }
        String prize = product.getProduct();
        System.out.println("恭喜您抽中了奖品:" + prize);

        System.out.println("------------------------------------------------------");

        ProductGetter<Integer> productGetter = new ProductGetter<>();
        int[] intProduct = {100, 200, 3000};
        for (int i = 0; i < intProduct.length; i++) {
            productGetter.addProduct(intProduct[i]);
        }

        Integer bonus = productGetter.getProduct();
        System.out.println("恭喜您抽中了奖金:" + bonus);
    }

    /**
     * 上限通配符使用
     */
    @Test
    void boxTest() {

        Box<Number> box1 = new Box<>();
        box1.setFirst(100);
        showBox(box1);

        Box<Integer> box2 = new Box<>();
        box2.setFirst(200);
        showBox(box2);

        Box<Float> box3 = new Box<>();
        box3.setFirst(0.3f);
        showBox(box3);

    }

    /**
     *  泛型通配符使用
     */
    @Test
    void superTest() {

        ArrayList<Animal> animals = new ArrayList<>();
        ArrayList<Cat> cats = new ArrayList<>();
        ArrayList<MiniCat> miniCats = new ArrayList<>();

        //upperLimitShowAnimal(animals);
        upperLimitShowAnimal(cats);
        upperLimitShowAnimal(miniCats);

        lowerLimitShowAnimal(animals);
        lowerLimitShowAnimal(cats);
        //lowerLimitShowAnimal(miniCats);


    }

    @Test
    void genericArray() {

    }


    @Test
    void invokeTest() {

        Proxy.newProxyInstance(UserServiceController.class.getClassLoader(),
                new Class[]{UserOrderFeign.class}, new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] objects) throws Throwable {


                        return null;
                    }
                });
    }

}
