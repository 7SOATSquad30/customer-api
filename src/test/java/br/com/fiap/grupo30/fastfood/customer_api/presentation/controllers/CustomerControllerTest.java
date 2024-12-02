public class CustomerControllerTest {

    private MockMvc mockMvc;

    // private final FindCustomerByCpfUseCase findCustomerByCpfUseCase;
    // private final RegisterNewCustomerUseCase registerNewCustomerUseCase;
    // private final JpaCustomerRepository jpaCustomerRepository;

    @Mock private ListAllCustomersInMenuUseCase listAllCustomersInMenuUseCase;
    @Mock private CustomerGateway customerGateway;

    @InjectMocks private CustomerController customerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    void findCustomerByCpfShouldReturnOneCustomer() throws Exception {
        // Arrange
        CustomerDTO customerDTO = new AddCustomerCpfRequest();
        customerDTO.setCpf("12345678900");
        when(listAllCustomersInMenuUseCase.execute(customerGateway))
                .thenReturn(List.of(customerDTO));

        // Act & Assert
        mockMvc.perform(get("/customers").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].name").value("John"));

        verify(listAllCustomersInMenuUseCase, times(1)).execute(customerGateway);
    }

    // public ResponseEntity<CustomerDTO> createCustomer(@RequestBody @Valid CustomerDTO dto)
    
}
