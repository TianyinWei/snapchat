public class BigInt { //reference: http://songyishan.iteye.com/blog/1026042
    private boolean sign = true;
    private byte[] digits;
    public BigInt() {
        this.digits = new byte[1];
        this.digits[0] = 0;
    }

    public BigInt(byte[] digits) {
        this.digits = digits;
    }

    private BigInt(String numberStr) {
        if (numberStr.charAt(0) == '-') {
            sign = false;
            StringBuilder sb = new StringBuilder(numberStr);
            sb.deleteCharAt(0);
            numberStr = new String(sb);
        }   else {
            sign = true;
        }
        digits = new byte[numberStr.length()];
        for (int i = 0; i < digits.length; i++) {
            switch(numberStr.charAt(i)) {
                case '0' : digits[i] = 0;   break;
                case '1': digits[i] = 1;break;   
                case '2': digits[i] = 2;break;   
                case '3': digits[i] = 3;break;   
                case '4': digits[i] = 4;break;   
                case '5': digits[i] = 5;break;   
                case '6': digits[i] = 6;break;   
                case '7': digits[i] = 7;break;   
                case '8': digits[i] = 8;break;   
                case '9': digits[i] = 9;break;   
                }
        }
    }


    public BigInt add(BigInt another) { 
    // YOU FILL THIS IN 
    BigInt sum = new BigInt(); 
    if(this.sign == another.sign){ 
        //the signs of both are equal 
        int length1 = this.digits.length; 
        int length2 = another.digits.length; 
        int biggerLength = Math.max(length1, length2); 
        byte[] temp = new byte[biggerLength]; 
        byte carry = 0; 
          
        for(int i = 1;i <= biggerLength;i++){ 
            byte i1 = (length1 - i < 0)?0:this.digits[length1 - i]; 
            byte i2 = (length2 - i < 0)?0:another.digits[length2 -i]; 
            int s = i1 + i2 + carry; 
            if(s < 10){ 
                temp[biggerLength - i] = (byte)s; 
                carry = 0; 
            }else{ 
                temp[biggerLength - i] = (byte)(s - 10); 
                carry = 1; 
            } 
        } 
          
        if(carry == 0){ 
            sum.digits = temp; 
        }else{ 
            sum.digits = new byte[biggerLength + 1]; 
            sum.digits[0] = carry; 
            for(int i = 0;i < biggerLength;i++){ 
                sum.digits[i + 1] = temp[i]; 
            } 
        } 
          
        sum.sign = this.sign; 
    }else{ 
        //the signs differ 
        boolean isAbsoluteEqual = false;//the default value is false 
        boolean isThisAbsoluteBigger = false;// the default value is false 
          
        if(this.digits.length > another.digits.length){ 
            isThisAbsoluteBigger = true; 
        }else if(this.digits.length == another.digits.length){ 
            isAbsoluteEqual = true; 
            for(int i = 0;i < this.digits.length;i++){ 
                if(this.digits[i] != another.digits[i]){ 
                    if(this.digits[i] > another.digits[i]){ 
                        isThisAbsoluteBigger = true; 
                    } 
                    isAbsoluteEqual = false; 
                    break; 
                } 
            } 
        } 
          
        //if isAbsoluteEqual is true, the sum should be 0, which is just the default value 
        if(!isAbsoluteEqual){ 
            byte[] temp; 
            byte[] bigger; 
            byte[] smaller; 
              
            if(isThisAbsoluteBigger){ 
                sum.sign = this.sign; 
                temp = new byte[this.digits.length]; 
                bigger = this.digits; 
                smaller = another.digits; 
            }else{ 
                sum.sign = another.sign; 
                temp = new byte[another.digits.length]; 
                bigger = another.digits; 
                smaller = this.digits; 
            } 
              
            boolean borrow = false; 
            for(int index = 1;index <= bigger.length;index++){ 
                byte biggerDigit = bigger[bigger.length - index]; 
                biggerDigit = (byte) ((borrow)?(biggerDigit - 1):biggerDigit); 
                byte smallerDigit = (smaller.length - index < 0)?0:smaller[smaller.length - index]; 
                int s = biggerDigit - smallerDigit; 
                if(s < 0){ 
                    borrow = true; 
                    s += 10; 
                }else{ 
                    borrow = false; 
                } 
                temp[temp.length - index] = (byte)s; 
            } 
              
            int zeroCount = 0; 
            for(int i = 0;i < temp.length;i++){ 
                if(temp[i] == 0){ 
                    zeroCount++; 
                }else{ 
                    break; 
                } 
            } 
            sum.digits = new byte[temp.length - zeroCount]; 
            for(int i = 0;i < sum.digits.length;i++){ 
                sum.digits[i] = temp[zeroCount + i]; 
            } 
        } 
    } 
    return sum; 
}

    private BigInt negate() {
        BigInt bi = new BigInt();
        byte[] digitsCopy = new byte[this.digits.length];
        for (int i = 0; i < this.digits.length; i++) {
            digitsCopy[i] = this.digits[i];
        }
        bi.digits = digitsCopy;
        bi.sign = !this.sign;
        return bi;
    }

    private BigInt subtract(BigInt another) {   // this - another
        return this.add(another.negate());
    }

    public static void main(String[] args) {
        BigInt num1 = new BigInt("-999");
        BigInt sum = num1.subtract(new BigInt("-888"));
        for (byte bit : sum.digits) {
            System.out.print(bit);
        }
        System.out.println(sum.sign);
    }
}

