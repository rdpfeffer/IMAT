FasdUAS 1.101.10   ��   ��    k             l      ��  ��   b\
 Copyright (c) 2009 Intuit, Inc.
 All rights reserved. This program and the accompanying materials
 are made available under the terms of the Eclipse Public License v1.0
 which accompanies this distribution, and is available at
 http://www.opensource.org/licenses/eclipse-1.0.php
 
 Contributors:
     Intuit, Inc - initial API and implementation
     � 	 	� 
   C o p y r i g h t   ( c )   2 0 0 9   I n t u i t ,   I n c . 
   A l l   r i g h t s   r e s e r v e d .   T h i s   p r o g r a m   a n d   t h e   a c c o m p a n y i n g   m a t e r i a l s 
   a r e   m a d e   a v a i l a b l e   u n d e r   t h e   t e r m s   o f   t h e   E c l i p s e   P u b l i c   L i c e n s e   v 1 . 0 
   w h i c h   a c c o m p a n i e s   t h i s   d i s t r i b u t i o n ,   a n d   i s   a v a i l a b l e   a t 
   h t t p : / / w w w . o p e n s o u r c e . o r g / l i c e n s e s / e c l i p s e - 1 . 0 . p h p 
   
   C o n t r i b u t o r s : 
           I n t u i t ,   I n c   -   i n i t i a l   A P I   a n d   i m p l e m e n t a t i o n 
   
  
 i         I     �� ��
�� .aevtoappnull  �   � ****  o      ���� 0 argv  ��    k    v       l     ��  ��    8 2Get the first argument and cast it to a Posix file     �   d G e t   t h e   f i r s t   a r g u m e n t   a n d   c a s t   i t   t o   a   P o s i x   f i l e      r         l     ����  I    �� ��
�� .corecnte****       ****  o     ���� 0 argv  ��  ��  ��    o      ���� 0 numargs numArgs      Z    #  ����  >        o    	���� 0 numargs numArgs   m   	 
����   k     ! !  " # " I   �� $��
�� .ascrcmnt****      � **** $ l    %���� % b     & ' & b     ( ) ( m     * * � + + ( N u m b e r   o f   a r g s   w a s :   ) l    ,���� , c     - . - o    ���� 0 numargs numArgs . m    ��
�� 
ctxt��  ��   ' m     / / � 0 0 P   1   a r g u m e n t   r e q u i r e d   t o   p e r f o r m   c l e a n u p .��  ��  ��   #  1�� 1 I   �� 2��
�� .ascrcmnt****      � **** 2 l    3���� 3 m     4 4 � 5 5 z U s a g e :   C l e a n U p X C o d e I n s t r u m e n t . s c p t   / p a t h / t o / a p p H o m e / l o g s / r u n s��  ��  ��  ��  ��  ��     6 7 6 r   $ , 8 9 8 c   $ * : ; : l  $ ( <���� < n   $ ( = > = 4   % (�� ?
�� 
cobj ? m   & '����  > o   $ %���� 0 argv  ��  ��   ; m   ( )��
�� 
TEXT 9 o      ���� 0 logpathstring logPathString 7  @ A @ r   - 3 B C B l  - 1 D���� D 4   - 1�� E
�� 
psxf E o   / 0���� 0 logpathstring logPathString��  ��   C o      ���� 0 logpath logPath A  F G F I  4 =�� H��
�� .ascrcmnt****      � **** H l  4 9 I���� I b   4 9 J K J m   4 5 L L � M M  l o g P a t h   i s :   K l  5 8 N���� N c   5 8 O P O o   5 6���� 0 logpath logPath P m   6 7��
�� 
ctxt��  ��  ��  ��  ��   G  Q R Q l  > >��������  ��  ��   R  S T S l  > >�� U V��   U 7 1Get the name of the directory we will be creating    V � W W b G e t   t h e   n a m e   o f   t h e   d i r e c t o r y   w e   w i l l   b e   c r e a t i n g T  X Y X r   > E Z [ Z n  > C \ ] \ I   ? C�������� 0 getdate getDate��  ��   ]  f   > ? [ o      ���� $0 targetfoldername targetFolderName Y  ^ _ ^ l  F F�� ` a��   ` / )Create a new directory to archive the Run    a � b b R C r e a t e   a   n e w   d i r e c t o r y   t o   a r c h i v e   t h e   R u n _  c d c O   F e e f e I  J d���� g
�� .corecrel****      � null��   g �� h i
�� 
kocl h m   N Q��
�� 
cfol i �� j k
�� 
insh j o   T U���� 0 logpath logPath k �� l��
�� 
prdt l K   X ^ m m �� n��
�� 
pnam n o   [ \���� $0 targetfoldername targetFolderName��  ��   f m   F G o o�                                                                                  MACS  alis    r  Macintosh HD               �j�H+   �
Finder.app                                                      �|�F!M        ����  	                CoreServices    �jp      �F��     � S S  3Macintosh HD:System:Library:CoreServices:Finder.app    
 F i n d e r . a p p    M a c i n t o s h   H D  &System/Library/CoreServices/Finder.app  / ��   d  p q p l  f f�� r s��   r L F Get references to the target dir which will hold the archived results    s � t t �   G e t   r e f e r e n c e s   t o   t h e   t a r g e t   d i r   w h i c h   w i l l   h o l d   t h e   a r c h i v e d   r e s u l t s q  u v u r   f q w x w l  f m y���� y b   f m z { z b   f k | } | o   f g���� 0 logpathstring logPathString } m   g j ~ ~ �    / { o   k l���� $0 targetfoldername targetFolderName��  ��   x o      ���� "0 targetdirstring targetDirString v  � � � r   r � � � � l  r | ����� � c   r | � � � l  r x ����� � 4   r x�� �
�� 
psxf � o   t w���� "0 targetdirstring targetDirString��  ��   � m   x {��
�� 
alis��  ��   � o      ���� 0 	targetdir 	targetDir �  � � � r   � � � � � m   � ���
�� boovfals � o      ���� *0 didwritetotargetdir didWriteToTargetDir �  � � � l  � ���������  ��  ��   �  � � � l  � ��� � ���   � @ :Archive the Run 1 folder in a folder with a Date/TimeStamp    � � � � t A r c h i v e   t h e   R u n   1   f o l d e r   i n   a   f o l d e r   w i t h   a   D a t e / T i m e S t a m p �  � � � Z   � � � ����� � I   � ��� ����� 0 
fileexists 
fileExists �  ��� � 4   � ��� �
�� 
psxf � l  � � ����� � b   � � � � � o   � ����� 0 logpathstring logPathString � m   � � � � � � �  / R u n   1��  ��  ��  ��   � k   � � � �  � � � I  � ��� ���
�� .ascrcmnt****      � **** � l  � � ����� � m   � � � � � � � * A r c h i v i n g   A c t i v e   R u n s��  ��  ��   �  � � � l  � ��� � ���   � G A Get reference to the source dir which we will delete afterwards.    � � � � �   G e t   r e f e r e n c e   t o   t h e   s o u r c e   d i r   w h i c h   w e   w i l l   d e l e t e   a f t e r w a r d s . �  � � � r   � � � � � l  � � ����� � c   � � � � � l  � � ����� � 4   � ��� �
�� 
psxf � l  � � ����� � b   � � � � � o   � ����� 0 logpathstring logPathString � m   � � � � � � �  / R u n   1��  ��  ��  ��   � m   � ���
�� 
alis��  ��   � o      ���� 0 	sourcedir 	sourceDir �  � � � l  � ��� � ���   � 0 *Move over the files and delete the Run dir    � � � � T M o v e   o v e r   t h e   f i l e s   a n d   d e l e t e   t h e   R u n   d i r �  � � � O   � � � � � k   � � � �  � � � I  � ��� � �
�� .coremoveobj        obj  � n   � � � � � 1   � ���
�� 
ects � o   � ����� 0 	sourcedir 	sourceDir � �� ���
�� 
insh � o   � ����� 0 	targetdir 	targetDir��   �  ��� � I  � ��� ���
�� .coredeloobj        obj  � o   � ����� 0 	sourcedir 	sourceDir��  ��   � m   � � � ��                                                                                  MACS  alis    r  Macintosh HD               �j�H+   �
Finder.app                                                      �|�F!M        ����  	                CoreServices    �jp      �F��     � S S  3Macintosh HD:System:Library:CoreServices:Finder.app    
 F i n d e r . a p p    M a c i n t o s h   H D  &System/Library/CoreServices/Finder.app  / ��   �  ��� � r   � � � � � m   � ���
�� boovtrue � o      ���� *0 didwritetotargetdir didWriteToTargetDir��  ��  ��   �  � � � l  � �����~��  �  �~   �  � � � l  � ��} � ��}   � O IAll of the other runs will be Manual runs since Instruments always starts    � � � � � A l l   o f   t h e   o t h e r   r u n s   w i l l   b e   M a n u a l   r u n s   s i n c e   I n s t r u m e n t s   a l w a y s   s t a r t s �  � � � l  � ��| � ��|   � < 6with "Run 1" when it executes. We will move these into    � � � � l w i t h   " R u n   1 "   w h e n   i t   e x e c u t e s .   W e   w i l l   m o v e   t h e s e   i n t o �  � � � Z   �Q � ��{�z � I   � ��y ��x�y 0 
fileexists 
fileExists �  ��w � 4   � ��v �
�v 
psxf � o   � ��u�u 0 logpathstring logPathString�w  �x   � k   �M � �  � � � I  � ��t ��s
�t .ascrcmnt****      � **** � l  � � ��r�q � m   � � � � � � � . C l e a n i n g   u p   m a n u a l   r u n s�r  �q  �s   �  � � � r   � � � � � l  � � ��p�o � c   � � � � � l  � � ��n�m � 4   � ��l �
�l 
psxf � o   � ��k�k 0 logpathstring logPathString�n  �m   � m   � ��j
�j 
alis�p  �o   � o      �i�i 0 runsdir runsDir �  � � � O   �K � � � k   �J � �  � � � e   � � � 6  � � � � n   � � � � 2  �h
�h 
cobj � o   ��g�g 0 runsdir runsDir � C    1  �f
�f 
pnam m   �  R u n �  r   1  �e
�e 
rslt o      �d�d 0 
manualruns 
manualRuns �c X  J	�b
	 k  0E  I 0?�a
�a .coremoveobj        obj  l 05�`�_ c  05 o  01�^�^ 0 	manualrun   m  14�]
�] 
alis�`  �_   �\�[
�\ 
insh o  8;�Z�Z 0 	targetdir 	targetDir�[   �Y r  @E m  @A�X
�X boovtrue o      �W�W *0 didwritetotargetdir didWriteToTargetDir�Y  �b 0 	manualrun  
 o  "�V�V 0 
manualruns 
manualRuns�c   � m   � ��                                                                                  MACS  alis    r  Macintosh HD               �j�H+   �
Finder.app                                                      �|�F!M        ����  	                CoreServices    �jp      �F��     � S S  3Macintosh HD:System:Library:CoreServices:Finder.app    
 F i n d e r . a p p    M a c i n t o s h   H D  &System/Library/CoreServices/Finder.app  / ��   � �U l LL�T�S�R�T  �S  �R  �U  �{  �z   �  l RR�Q�P�O�Q  �P  �O   �N Z  Rv�M o  RU�L�L *0 didwritetotargetdir didWriteToTargetDir I X_�K�J
�K .ascrcmnt****      � **** l X[ �I�H  m  X[!! �"" " C l e a n u p   C o m p l e t e .�I  �H  �J  �M   k  bv## $%$ I bi�G&�F
�G .ascrcmnt****      � ****& l be'�E�D' m  be(( �)) 6 D e l e t i n g   U n u s e d   A r c h i v e   D i r�E  �D  �F  % *�C* O  jv+,+ I nu�B-�A
�B .coredeloobj        obj - o  nq�@�@ 0 	targetdir 	targetDir�A  , m  jk..�                                                                                  MACS  alis    r  Macintosh HD               �j�H+   �
Finder.app                                                      �|�F!M        ����  	                CoreServices    �jp      �F��     � S S  3Macintosh HD:System:Library:CoreServices:Finder.app    
 F i n d e r . a p p    M a c i n t o s h   H D  &System/Library/CoreServices/Finder.app  / ��  �C  �N    /0/ l     �?�>�=�?  �>  �=  0 121 l      �<34�<  3 V P
Get the Current Date converted to the ISO 8601:2004 
international date format
   4 �55 � 
 G e t   t h e   C u r r e n t   D a t e   c o n v e r t e d   t o   t h e   I S O   8 6 0 1 : 2 0 0 4   
 i n t e r n a t i o n a l   d a t e   f o r m a t 
2 676 i    898 I      �;�:�9�; 0 getdate getDate�:  �9  9 k     m:: ;<; r     =>= I    �8�7�6
�8 .misccurdldt    ��� null�7  �6  > o      �5�5 0 now  < ?@? r    ABA m    	CC �DD  -B o      �4�4 0 	delimiter  @ EFE r    GHG c    IJI n    KLK 1    �3
�3 
yearL o    �2�2 0 now  J m    �1
�1 
TEXTH o      �0�0 0 year_string  F MNM r    "OPO I     �/Q�.�/ 60 appendzerostodoubledigits appendZerosToDoubleDigitsQ R�-R c    STS c    UVU n    WXW m    �,
�, 
mnthX o    �+�+ 0 now  V m    �*
�* 
longT m    �)
�) 
TEXT�-  �.  P o      �(�( 0 month_string  N YZY r   # /[\[ I   # -�']�&�' 60 appendzerostodoubledigits appendZerosToDoubleDigits] ^�%^ c   $ )_`_ n   $ 'aba 1   % '�$
�$ 
day b o   $ %�#�# 0 now  ` m   ' (�"
�" 
TEXT�%  �&  \ o      �!�! 0 
day_string  Z cdc r   0 <efe I   0 :� g��  60 appendzerostodoubledigits appendZerosToDoubleDigitsg h�h c   1 6iji n   1 4klk 1   2 4�
� 
hourl o   1 2�� 0 now  j m   4 5�
� 
TEXT�  �  f o      �� 0 hour_string  d mnm r   = Iopo I   = G�q�� 60 appendzerostodoubledigits appendZerosToDoubleDigitsq r�r c   > Csts n   > Auvu 1   ? A�
� 
min v o   > ?�� 0 now  t m   A B�
� 
TEXT�  �  p o      �� 0 minute_string  n wxw r   J Vyzy I   J T�{�� 60 appendzerostodoubledigits appendZerosToDoubleDigits{ |�| c   K P}~} n   K N� m   L N�
� 
scnd� o   K L�� 0 now  ~ m   N O�
� 
TEXT�  �  z o      �� 0 second_string  x ��� l  W W��
�	�  �
  �	  � ��� L   W m�� b   W l��� b   W j��� b   W h��� b   W f��� b   W d��� b   W b��� b   W `��� b   W ^��� b   W \��� b   W Z��� o   W X�� 0 year_string  � l 
 X Y���� o   X Y�� 0 	delimiter  �  �  � o   Z [�� 0 month_string  � l 
 \ ]���� o   \ ]� �  0 	delimiter  �  �  � o   ^ _���� 0 
day_string  � l 	 ` a������ m   ` a�� ���  _��  ��  � o   b c���� 0 hour_string  � l 
 d e������ o   d e���� 0 	delimiter  ��  ��  � o   f g���� 0 minute_string  � l 
 h i������ o   h i���� 0 	delimiter  ��  ��  � o   j k���� 0 second_string  �  7 ��� l     ��������  ��  ��  � ��� l      ������  � � �
Append Leading zeros to a string if it is only of length 1. Returns the numeric 
string with appended zeros. Will return the given string if its 
length is not equal to 1
   � ���X 
 A p p e n d   L e a d i n g   z e r o s   t o   a   s t r i n g   i f   i t   i s   o n l y   o f   l e n g t h   1 .   R e t u r n s   t h e   n u m e r i c   
 s t r i n g   w i t h   a p p e n d e d   z e r o s .   W i l l   r e t u r n   t h e   g i v e n   s t r i n g   i f   i t s   
 l e n g t h   i s   n o t   e q u a l   t o   1 
� ��� i    ��� I      ������� 60 appendzerostodoubledigits appendZerosToDoubleDigits� ���� o      ���� 0 numericstring numericString��  ��  � k     �� ��� Z     ������� =    ��� n     ��� 1    ��
�� 
leng� o     ���� 0 numericstring numericString� m    ���� � r    ��� b    ��� m    	�� ���  0� o   	 
���� 0 numericstring numericString� o      ���� 0 numericstring numericString��  ��  � ���� L    �� o    ���� 0 numericstring numericString��  � ��� l     ��������  ��  ��  � ��� l      ������  � ? 9
Return true if the passed file exists, false otherwise.
   � ��� r 
 R e t u r n   t r u e   i f   t h e   p a s s e d   f i l e   e x i s t s ,   f a l s e   o t h e r w i s e . 
� ���� i    ��� I      ������� 0 
fileexists 
fileExists� ���� o      ���� 0 fileorfolder fileOrFolder��  ��  � Q     ���� k    �� ��� 4    ���
�� 
alis� o    ���� 0 fileorfolder fileOrFolder� ���� L   	 �� m   	 
��
�� boovtrue��  � R      ������
�� .ascrerr ****      � ****��  ��  � L    �� m    ��
�� boovfals��       ���������  � ��������
�� .aevtoappnull  �   � ****�� 0 getdate getDate�� 60 appendzerostodoubledigits appendZerosToDoubleDigits�� 0 
fileexists 
fileExists� �� ��������
�� .aevtoappnull  �   � ****�� 0 argv  ��  � ������ 0 argv  �� 0 	manualrun  � ,���� *�� /�� 4���������� L���� o�������������� ~�������� ��� � ��������� ��������!(
�� .corecnte****       ****�� 0 numargs numArgs
�� 
ctxt
�� .ascrcmnt****      � ****
�� 
cobj
�� 
TEXT�� 0 logpathstring logPathString
�� 
psxf�� 0 logpath logPath�� 0 getdate getDate�� $0 targetfoldername targetFolderName
�� 
kocl
�� 
cfol
�� 
insh
�� 
prdt
�� 
pnam�� 
�� .corecrel****      � null�� "0 targetdirstring targetDirString
�� 
alis�� 0 	targetdir 	targetDir�� *0 didwritetotargetdir didWriteToTargetDir�� 0 
fileexists 
fileExists�� 0 	sourcedir 	sourceDir
�� 
ects
�� .coremoveobj        obj 
�� .coredeloobj        obj �� 0 runsdir runsDir�  
�� 
rslt�� 0 
manualruns 
manualRuns��w�j  E�O�k ���&%�%j O�j Y hO��k/�&E�O*��/E�O���&%j O)j+ E�O� *a a a �a a �la  UO�a %�%E` O*�_ /a &E` OfE` O**��a %/k+  Ba j O*��a %/a &E`  O� _  a !,a _ l "O_  j #UOeE` Y hO**��/k+  ma $j O*��/a &E` %O� N_ %�-a &[a ,\Za '>1EO_ (E` )O -_ )[a �l  kh �a &a _ l "OeE` [OY��UOPY hO_  a *j Y a +j O� 	_ j #U� ��9���������� 0 getdate getDate��  ��  � ������������������ 0 now  �� 0 	delimiter  �� 0 year_string  �� 0 month_string  �� 0 
day_string  �� 0 hour_string  �� 0 minute_string  �� 0 second_string  � ��C�������������������
�� .misccurdldt    ��� null
�� 
year
�� 
TEXT
�� 
mnth
�� 
long�� 60 appendzerostodoubledigits appendZerosToDoubleDigits
�� 
day 
�� 
hour
�� 
min 
�� 
scnd�� n*j  E�O�E�O��,�&E�O*��,�&�&k+ E�O*��,�&k+ E�O*��,�&k+ E�O*��,�&k+ E�O*��,�&k+ E�O��%�%�%�%�%�%�%�%�%�%� ������������� 60 appendzerostodoubledigits appendZerosToDoubleDigits�� ����� �  ���� 0 numericstring numericString��  � ���� 0 numericstring numericString� ���
�� 
leng�� ��,k  
�%E�Y hO�� ������������� 0 
fileexists 
fileExists�� ����� �  ���� 0 fileorfolder fileOrFolder��  � ���� 0 fileorfolder fileOrFolder� ������
�� 
alis��  ��  ��  *�/EOeW 	X  fascr  ��ޭ