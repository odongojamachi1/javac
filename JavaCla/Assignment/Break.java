
// package Assinment;
// package Break ;

// public class Break {

//     public static void main(String[] args) {
//         int day = 3;
//         switch (day) {
//             case 1:
//                 System.out.println("Monday");
//                 break;
//             case 2:
//                 System.out.println("Tuesday");
//                 break;
//             case 3:
//                 System.out.println("Wednesday");
//                 break;
//             default:
//                 System.out.println("Unknown day");
//         }
//     }
// }

public class Break {
    public static void main(String[] args) {
      for (int i = 0; i < 10; i++) {
        if (i == 4) {
          break;
        }
        System.out.println(i);
      }  
    }
  }
  