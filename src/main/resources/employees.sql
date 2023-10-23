PGDMP  "    +            	    {        	   employees     16.0 (Ubuntu 16.0-1.pgdg22.04+1)     16.0 (Ubuntu 16.0-1.pgdg22.04+1) #    L           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            M           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            N           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            O           1262    16389 	   employees    DATABASE     u   CREATE DATABASE employees WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'en_US.UTF-8';
    DROP DATABASE employees;
                hsein    false            �            1259    16430    _user    TABLE     �   CREATE TABLE public._user (
    id integer NOT NULL,
    email character varying(255),
    firstname character varying(255),
    lastname character varying(255),
    password character varying(255),
    role character varying(255)
);
    DROP TABLE public._user;
       public         heap    hsein    false            �            1259    16429    _user_id_seq    SEQUENCE     �   CREATE SEQUENCE public._user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 #   DROP SEQUENCE public._user_id_seq;
       public          hsein    false    222            P           0    0    _user_id_seq    SEQUENCE OWNED BY     =   ALTER SEQUENCE public._user_id_seq OWNED BY public._user.id;
          public          hsein    false    221            �            1259    16416    departments    TABLE     �   CREATE TABLE public.departments (
    id bigint NOT NULL,
    description character varying(255),
    name character varying(255) NOT NULL
);
    DROP TABLE public.departments;
       public         heap    hsein    false            �            1259    16415    departments_id_seq    SEQUENCE     {   CREATE SEQUENCE public.departments_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 )   DROP SEQUENCE public.departments_id_seq;
       public          hsein    false    220            Q           0    0    departments_id_seq    SEQUENCE OWNED BY     I   ALTER SEQUENCE public.departments_id_seq OWNED BY public.departments.id;
          public          hsein    false    219            �            1259    16391 	   employees    TABLE     �   CREATE TABLE public.employees (
    id bigint NOT NULL,
    email_id character varying(255),
    first_name character varying(255),
    last_name character varying(255),
    job_title_id bigint
);
    DROP TABLE public.employees;
       public         heap    hsein    false            �            1259    16390    employees_id_seq    SEQUENCE     y   CREATE SEQUENCE public.employees_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 '   DROP SEQUENCE public.employees_id_seq;
       public          hsein    false    216            R           0    0    employees_id_seq    SEQUENCE OWNED BY     E   ALTER SEQUENCE public.employees_id_seq OWNED BY public.employees.id;
          public          hsein    false    215            �            1259    16400 
   job_titles    TABLE     �   CREATE TABLE public.job_titles (
    id bigint NOT NULL,
    description character varying(255),
    title character varying(255) NOT NULL,
    department_id bigint
);
    DROP TABLE public.job_titles;
       public         heap    hsein    false            �            1259    16399    job_titles_id_seq    SEQUENCE     z   CREATE SEQUENCE public.job_titles_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 (   DROP SEQUENCE public.job_titles_id_seq;
       public          hsein    false    218            S           0    0    job_titles_id_seq    SEQUENCE OWNED BY     G   ALTER SEQUENCE public.job_titles_id_seq OWNED BY public.job_titles.id;
          public          hsein    false    217            �           2604    16433    _user id    DEFAULT     d   ALTER TABLE ONLY public._user ALTER COLUMN id SET DEFAULT nextval('public._user_id_seq'::regclass);
 7   ALTER TABLE public._user ALTER COLUMN id DROP DEFAULT;
       public          hsein    false    221    222    222            �           2604    16419    departments id    DEFAULT     p   ALTER TABLE ONLY public.departments ALTER COLUMN id SET DEFAULT nextval('public.departments_id_seq'::regclass);
 =   ALTER TABLE public.departments ALTER COLUMN id DROP DEFAULT;
       public          hsein    false    219    220    220            �           2604    16394    employees id    DEFAULT     l   ALTER TABLE ONLY public.employees ALTER COLUMN id SET DEFAULT nextval('public.employees_id_seq'::regclass);
 ;   ALTER TABLE public.employees ALTER COLUMN id DROP DEFAULT;
       public          hsein    false    215    216    216            �           2604    16403    job_titles id    DEFAULT     n   ALTER TABLE ONLY public.job_titles ALTER COLUMN id SET DEFAULT nextval('public.job_titles_id_seq'::regclass);
 <   ALTER TABLE public.job_titles ALTER COLUMN id DROP DEFAULT;
       public          hsein    false    218    217    218            I          0    16430    _user 
   TABLE DATA           O   COPY public._user (id, email, firstname, lastname, password, role) FROM stdin;
    public          hsein    false    222   �%       G          0    16416    departments 
   TABLE DATA           <   COPY public.departments (id, description, name) FROM stdin;
    public          hsein    false    220   �&       C          0    16391 	   employees 
   TABLE DATA           V   COPY public.employees (id, email_id, first_name, last_name, job_title_id) FROM stdin;
    public          hsein    false    216   z'       E          0    16400 
   job_titles 
   TABLE DATA           K   COPY public.job_titles (id, description, title, department_id) FROM stdin;
    public          hsein    false    218   !)       T           0    0    _user_id_seq    SEQUENCE SET     ;   SELECT pg_catalog.setval('public._user_id_seq', 35, true);
          public          hsein    false    221            U           0    0    departments_id_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('public.departments_id_seq', 4, true);
          public          hsein    false    219            V           0    0    employees_id_seq    SEQUENCE SET     ?   SELECT pg_catalog.setval('public.employees_id_seq', 58, true);
          public          hsein    false    215            W           0    0    job_titles_id_seq    SEQUENCE SET     ?   SELECT pg_catalog.setval('public.job_titles_id_seq', 8, true);
          public          hsein    false    217            �           2606    16442    _user _user_email_key 
   CONSTRAINT     Q   ALTER TABLE ONLY public._user
    ADD CONSTRAINT _user_email_key UNIQUE (email);
 ?   ALTER TABLE ONLY public._user DROP CONSTRAINT _user_email_key;
       public            hsein    false    222            �           2606    16437    _user _user_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public._user
    ADD CONSTRAINT _user_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public._user DROP CONSTRAINT _user_pkey;
       public            hsein    false    222            �           2606    16423    departments departments_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.departments
    ADD CONSTRAINT departments_pkey PRIMARY KEY (id);
 F   ALTER TABLE ONLY public.departments DROP CONSTRAINT departments_pkey;
       public            hsein    false    220            �           2606    16398    employees employees_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY public.employees
    ADD CONSTRAINT employees_pkey PRIMARY KEY (id);
 B   ALTER TABLE ONLY public.employees DROP CONSTRAINT employees_pkey;
       public            hsein    false    216            �           2606    16407    job_titles job_titles_pkey 
   CONSTRAINT     X   ALTER TABLE ONLY public.job_titles
    ADD CONSTRAINT job_titles_pkey PRIMARY KEY (id);
 D   ALTER TABLE ONLY public.job_titles DROP CONSTRAINT job_titles_pkey;
       public            hsein    false    218            �           2606    16424 &   job_titles fk_job_titles_department_id    FK CONSTRAINT     �   ALTER TABLE ONLY public.job_titles
    ADD CONSTRAINT fk_job_titles_department_id FOREIGN KEY (department_id) REFERENCES public.departments(id);
 P   ALTER TABLE ONLY public.job_titles DROP CONSTRAINT fk_job_titles_department_id;
       public          hsein    false    218    220    3244            �           2606    16410 %   employees fkiftfevnnkbty768nwfa4nsea8    FK CONSTRAINT     �   ALTER TABLE ONLY public.employees
    ADD CONSTRAINT fkiftfevnnkbty768nwfa4nsea8 FOREIGN KEY (job_title_id) REFERENCES public.job_titles(id);
 O   ALTER TABLE ONLY public.employees DROP CONSTRAINT fkiftfevnnkbty768nwfa4nsea8;
       public          hsein    false    3242    216    218            I     x�M��n�@ @���.X���G)HhI���:�P��oMc��N�p���ע�K¥u��>���=�2fE����n��\^��&g�W�a��<����n6�lA��^GT��6���%�qg3>����'b��������VSv��ܫb
��X�W�F�׵fOm4��N�?�=rq�.ogJ��&��м�O�CT�ޘ��r
m+�w�8;�$�U��j�A�X������fG_�0�(�dc      G   �   x��α1�:��  ���1��,�%>$����������0l)c��A���V>	�9�t'I����p@�CR��½U9�Jj�N���?j����`�~��_���]��(��#�����=h�"n�D`o���ǅ��Z�g�      C   �  x����r�0�ϛ��	 	�^:�N�b�^V	������`�"�������9�A�rn�V�S۸s�RJ0e����R���ţ6���=�/(� �C/L�Q⻆�ģ��UABh>E�mx�1)a1ld��͝a�u�ٔX�(i�g��4�Vb��6�j݋N�L_+����oF�z�p�L�}�#��Щ����ʆ�ఃm�	G���t:ҝ'3E�AWÄ�B�F�B����'^�k�?:�гp2�Ͼ�N���ϣ|�\B6�g�\����Fa`����q:{��=��f��>������H��$��|�����=�nN� )[w������7Nhp��6D���!#,�פ�e�䄭��Z����Y/<�/���*�O!Nx��pt�	gO��a�r�,yXB~���      E   �   x���1n�0Eg�<A�8h�5F�C���ű�X�J�T÷/� K���������8HB��3w��E(�n����
�ʽ ���C�}�}���f�
��)rFoJL�)�b��$Ƣ�KD��{�����?8Sb���z�:��U{�˨�+��So7�[�ʽ�APjC�[�&ؕ�-kW��OY�k~�otƭD���!���T�*�|v�Z��'��?��~     