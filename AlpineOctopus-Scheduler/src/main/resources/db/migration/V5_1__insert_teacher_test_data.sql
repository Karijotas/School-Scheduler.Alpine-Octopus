INSERT INTO TEACHER (NAME,SURNAME,LOGIN_EMAIL,
                     CONTACT_EMAIL, PHONE,
                     WORK_HOURS_PER_WEEK,
                     SHIFT) VALUES          ('Mantvydas', 'Juršys', 'mantvydas@loginEmail.lt',
                                             'mantvydas@contactEmail.lt', '+370 123 321 12', 40, 'Rytas');



/* TODO: SUBJECTS_LIST  */


/*

  @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;
    private String loginEmail;
    private String contactEmail;
    private String phone;
    private double workHoursPerWeek;

    //@ManyToMany(mappedBy = subjectEntity)
    //private List<String> subjectsList;

    private String shift;
// variables end



 */