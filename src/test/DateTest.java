package test;

import main.util.Date;
import main.util.TimeZone;

public class DateTest {
    public static void main(String[] args) {
        Date test0 = new Date(31536001234l);
        Date test1 = new Date(68169599999l);
        Date test2 = new Date(68169600000l);
        Date test3 = new Date(68255999999l);
        Date test4 = new Date(68256000000l);
        Date test5 = new Date(94694399999l);
        Date test6 = new Date(94694400000l);
        Date test7 = new Date(-68255999999l);
        
        System.out.println(test0.format("EEE dd MMMM yyyy [at] hh:mm:ss.SSS aa zzz")); // Fri 01 January 1971 at 12:00:01.234 AM UTC
        System.out.println(test1.format("EEE dd MMMM yyyy [at] hh:mm:ss.SSS aa zzz")); // Mon 28 February 1972 at 11:59:59.999 PM UTC
        System.out.println(test2.format("EEE dd MMMM yyyy [at] hh:mm:ss.SSS aa zzz")); // Tue 29 February 1972 at 12:00:00.000 AM UTC
        System.out.println(test3.format("EEE dd MMMM yyyy [at] hh:mm:ss.SSS aa zzz")); // Tue 29 February 1972 at 11:59:59.999 PM UTC
        System.out.println(test4.format("EEE dd MMMM yyyy [at] hh:mm:ss.SSS aa zzz")); // Wed 01 March 1972 at 12:00:00.000 AM UTC
        System.out.println(test5.format("EEE dd MMMM yyyy [at] hh:mm:ss.SSS aa zzz")); // Sun 31 December 1972 at 11:59:59.999 PM UTC
        System.out.println(test6.format("EEE dd MMMM yyyy [at] hh:mm:ss.SSS aa zzz")); // Mon 01 January 1973 at 12:00:00.000 AM UTC
        System.out.println(test7.format("EEE dd MMMM yyyy [at] hh:mm:ss.SSS aa zzz")); // Fri 03 November 1967 at 12:00:00.001 AM UTC

        System.out.println("==========================================");

        Date test0String = new Date("1971-01-01 00:00:01.234 UTC");
        Date test1String = new Date("1972-02-28 23:59:59.999 UTC");
        Date test2String = new Date("1972-02-29 00:00:00.000 UTC");
        Date test3String = new Date("1972-02-29 23:59:59.999 UTC");
        Date test4String = new Date("1972-03-01 00:00:00.000 UTC");
        Date test5String = new Date("1972-12-31 23:59:59.999 UTC");
        Date test6String = new Date("1973-01-01 00:00:00.000 UTC");
        Date test7String = new Date("1967-11-03 00:00:00.001 UTC");

        System.out.println(test0String.format("EEE dd MMMM yyyy [at] hh:mm:ss.SSS aa zzz")); // Fri 01 January 1971 at 12:00:00.000 AM UTC
        System.out.println(test1String.format("EEE dd MMMM yyyy [at] hh:mm:ss.SSS aa zzz")); // Mon 28 February 1972 at 11:59:59.999 PM UTC
        System.out.println(test2String.format("EEE dd MMMM yyyy [at] hh:mm:ss.SSS aa zzz")); // Tue 29 February 1972 at 12:00:00.000 AM UTC
        System.out.println(test3String.format("EEE dd MMMM yyyy [at] hh:mm:ss.SSS aa zzz")); // Tue 29 February 1972 at 11:59:59.999 PM UTC
        System.out.println(test4String.format("EEE dd MMMM yyyy [at] hh:mm:ss.SSS aa zzz")); // Wed 01 March 1972 at 12:00:00.000 AM UTC
        System.out.println(test5String.format("EEE dd MMMM yyyy [at] hh:mm:ss.SSS aa zzz")); // Sun 31 December 1972 at 11:59:59.999 PM UTC
        System.out.println(test6String.format("EEE dd MMMM yyyy [at] hh:mm:ss.SSS aa zzz")); // Mon 01 January 1973 at 12:00:00.000 AM UTC
        System.out.println(test7String.format("EEE dd MMMM yyyy [at] hh:mm:ss.SSS aa zzz")); // Fri 03 November 1967 at 12:00:00.001 AM UTC

        System.out.println("==========================================");
        
        Date test0EST = new Date(31536001234l, TimeZone.EST);
        Date test1EST = new Date(68169599999l, TimeZone.EST);
        Date test2EST = new Date(68169600000l, TimeZone.EST);
        Date test3EST = new Date(68255999999l, TimeZone.EST);
        Date test4EST = new Date(68256000000l, TimeZone.EST);
        Date test5EST = new Date(94694399999l, TimeZone.EST);
        Date test6EST = new Date(94694400000l, TimeZone.EST);
        Date test7EST = new Date(-68255999999l, TimeZone.EST);

        System.out.println(test0EST.format("EEE dd MMMM yyyy [at] hh:mm:ss.SSS aa zzz")); // Thu 31 December 1970 at 07:00:01.234 PM EST
        System.out.println(test1EST.format("EEE dd MMMM yyyy [at] hh:mm:ss.SSS aa zzz")); // Mon 28 February 1972 at 06:59:59.999 PM EST
        System.out.println(test2EST.format("EEE dd MMMM yyyy [at] hh:mm:ss.SSS aa zzz")); // Mon 28 February 1972 at 07:00:00.000 PM EST
        System.out.println(test3EST.format("EEE dd MMMM yyyy [at] hh:mm:ss.SSS aa zzz")); // Tue 29 February 1972 at 06:59:59.999 PM EST
        System.out.println(test4EST.format("EEE dd MMMM yyyy [at] hh:mm:ss.SSS aa zzz")); // Tue 29 February 1972 at 07:00:00.000 PM EST
        System.out.println(test5EST.format("EEE dd MMMM yyyy [at] hh:mm:ss.SSS aa zzz")); // Sun 31 December 1972 at 06:59:59.999 PM EST
        System.out.println(test6EST.format("EEE dd MMMM yyyy [at] hh:mm:ss.SSS aa zzz")); // Sun 31 December 1972 at 07:00:00.000 PM EST
        System.out.println(test7EST.format("EEE dd MMMM yyyy [at] hh:mm:ss.SSS aa zzz")); // Thu 02 November 1967 at 07:00:00.001 PM EST
    
        System.out.println("==========================================");

        Date test0ESTString = new Date("1970-12-31 19:00:01.234 EST");
        Date test1ESTString = new Date("1972-02-28 18:59:59.999 EST");
        Date test2ESTString = new Date("1972-02-28 19:00:00.000 EST");
        Date test3ESTString = new Date("1972-02-29 18:59:59.999 EST");
        Date test4ESTString = new Date("1972-02-29 19:00:00.000 EST");
        Date test5ESTString = new Date("1972-12-31 18:59:59.999 EST");
        Date test6ESTString = new Date("1972-12-31 19:00:00.000 EST");
        Date test7ESTString = new Date("1967-11-02 19:00:00.001 EST");

        System.out.println(test0ESTString.format("EEE dd MMMM yyyy [at] hh:mm:ss.SSS aa zzz")); // Thu 31 December 1970 at 07:00:01.234 PM EST
        System.out.println(test1ESTString.format("EEE dd MMMM yyyy [at] hh:mm:ss.SSS aa zzz")); // Mon 28 February 1972 at 06:59:59.999 PM EST
        System.out.println(test2ESTString.format("EEE dd MMMM yyyy [at] hh:mm:ss.SSS aa zzz")); // Mon 28 February 1972 at 07:00:00.000 PM EST
        System.out.println(test3ESTString.format("EEE dd MMMM yyyy [at] hh:mm:ss.SSS aa zzz")); // Tue 29 February 1972 at 06:59:59.999 PM EST
        System.out.println(test4ESTString.format("EEE dd MMMM yyyy [at] hh:mm:ss.SSS aa zzz")); // Tue 29 February 1972 at 07:00:00.000 PM EST
        System.out.println(test5ESTString.format("EEE dd MMMM yyyy [at] hh:mm:ss.SSS aa zzz")); // Sun 31 December 1972 at 06:59:59.999 PM EST
        System.out.println(test6ESTString.format("EEE dd MMMM yyyy [at] hh:mm:ss.SSS aa zzz")); // Sun 31 December 1972 at 07:00:00.000 PM EST
        System.out.println(test7ESTString.format("EEE dd MMMM yyyy [at] hh:mm:ss.SSS aa zzz")); // Thu 02 November 1967 at 07:00:00.001 PM EST

        System.out.println("==========================================");

        Date utcConvertTest0 = test0ESTString.toUTCDate();
        Date utcConvertTest1 = test1ESTString.toUTCDate();
        Date utcConvertTest2 = test2ESTString.toUTCDate();
        Date utcConvertTest3 = test3ESTString.toUTCDate();
        Date utcConvertTest4 = test4ESTString.toUTCDate();
        Date utcConvertTest5 = test5ESTString.toUTCDate();
        Date utcConvertTest6 = test6ESTString.toUTCDate();
        Date utcConvertTest7 = test7ESTString.toUTCDate();
        
        System.out.println(utcConvertTest0.format("EEE dd MMMM yyyy [at] hh:mm:ss.SSS aa zzz")); // Thu 31 December 1970 at 12:00:01.234 AM UTC
        System.out.println(utcConvertTest1.format("EEE dd MMMM yyyy [at] hh:mm:ss.SSS aa zzz")); // Mon 28 February 1972 at 11:59:59.999 PM UTC
        System.out.println(utcConvertTest2.format("EEE dd MMMM yyyy [at] hh:mm:ss.SSS aa zzz")); // Mon 28 February 1972 at 12:00:00.000 AM UTC
        System.out.println(utcConvertTest3.format("EEE dd MMMM yyyy [at] hh:mm:ss.SSS aa zzz")); // Tue 29 February 1972 at 11:59:59.999 PM UTC
        System.out.println(utcConvertTest4.format("EEE dd MMMM yyyy [at] hh:mm:ss.SSS aa zzz")); // Tue 29 February 1972 at 12:00:00.000 AM UTC
        System.out.println(utcConvertTest5.format("EEE dd MMMM yyyy [at] hh:mm:ss.SSS aa zzz")); // Sun 31 December 1972 at 11:59:59.999 PM UTC
        System.out.println(utcConvertTest6.format("EEE dd MMMM yyyy [at] hh:mm:ss.SSS aa zzz")); // Sun 31 December 1972 at 12:00:00.000 AM UTC
        System.out.println(utcConvertTest7.format("EEE dd MMMM yyyy [at] hh:mm:ss.SSS aa zzz")); // Thu 02 November 1967 at 12:00:00.001 AM UTC
    }
}
