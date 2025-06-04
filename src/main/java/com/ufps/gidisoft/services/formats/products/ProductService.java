package com.ufps.gidisoft.services.formats.products;

import com.ufps.gidisoft.entities.formats.events.Event;
import com.ufps.gidisoft.entities.formats.products.Product;
import com.ufps.gidisoft.entities.users.User;
import com.ufps.gidisoft.enums.exceptions.ExceptionCodeEnum;
import com.ufps.gidisoft.repositories.formats.products.ProductRepository;
import com.ufps.gidisoft.requests.formats.EventRequest;
import com.ufps.gidisoft.requests.formats.ProductRequest;
import com.ufps.gidisoft.responses.format.ProductDto;
import com.ufps.gidisoft.services.cloudinary.CloudinaryService;
import com.ufps.gidisoft.services.formats.general.FormatServiceSec;
import com.ufps.gidisoft.services.users.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductService {

    /*
     * Repositories
     */
    private final ProductRepository productRepository;

    /*
     * Services
     */
    private final UserService userService;
    private final FormatServiceSec formatService;
    private final CloudinaryService cloudinaryService;
    private final ProductUserService productUserService;
    private final ProductTypeService productTypeService;

    public Product findById(Long id){
        return productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(ExceptionCodeEnum.PROJ01.getMessage()
        ));
    }

    @Transactional
    public void createProduct(ProductRequest productRequest, User user) throws IOException {
        Product product = new Product();
        product.setDescription(productRequest.getDescription());
        product.setResponsibles(productRequest.getResponsibles());
        product.setDate(productRequest.getDate());
        product.setFormat(this.formatService.findByIdToRelations(productRequest.getFormatId()));
        product.setCreatedBy(user);
        product.setProductType(this.productTypeService.findProductTypeById(productRequest.getProductTypeId()));
        this.getFilesNameList(productRequest, product);
        this.productRepository.save(product);
        this.productUserService.createProductUser(product, user);
    }

    @Transactional
    public void updateProduct(ProductRequest productRequest, User user) throws IOException {
        Product product = findById(productRequest.getId());
        if(this.productUserService.validateExistProductAndUser(product, user)) {
            product.setDescription(productRequest.getDescription());
            product.setResponsibles(productRequest.getResponsibles());
            product.setDate(productRequest.getDate());
            this.getFilesNameList(productRequest, product);
            this.productRepository.save(product);
        }else throw new IllegalArgumentException(ExceptionCodeEnum.PROJ01.getMessage());
    }

    private void getFilesNameList(ProductRequest productRequest, Product product) throws IOException {
        if (productRequest.getFiles() != null && !productRequest.getFiles().isEmpty()) {
            List<String> files = new ArrayList<>();
            if (product.getFiles() != null && !product.getFiles().isEmpty()) {
                files = product.getFiles();
            }
            for (MultipartFile file : productRequest.getFiles()) {
                files.add(cloudinaryService.upload(file, "projects"));
            }
            product.setFiles(files);
        }
    }

    public Map<String, List<ProductDto>> findByFormatIdGrouped(Long formatId) {
        Map<String, List<ProductDto>> groupedProducts = new HashMap<>();

        for (Product product : this.productRepository.findByFormatId(formatId)) {
            String type = product.getProductType().getId().toString(); // o product.getType(), según tu modelo
            ProductDto dto = new ProductDto(product, this.productUserService.findUsersByProduct(product.getId()));

            groupedProducts.computeIfAbsent(type, k -> new ArrayList<>()).add(dto);
        }

        // (Opcional) ordena los productos por ID
        for (List<ProductDto> dtos : groupedProducts.values()) {
            dtos.sort(Comparator.comparing(ProductDto::getId));
        }

        return groupedProducts;
    }

    @Transactional
    public void deleteById(Long id) throws Exception {
        this.productUserService.deleteByProduct(id);
        Product product = findById(id);
        if (product.getFiles() != null && !product.getFiles().isEmpty()) {
            for (String file : product.getFiles()) {
                this.cloudinaryService.getImage(file);
            }
        }
        this.productRepository.deleteById(id);
    }

    @Transactional
    public void deleteEvidence(Long productId, String url) throws Exception {
        Product product = findById(productId);
        this.cloudinaryService.getImage(url);

        List<String> files = product.getFiles();
        files.removeIf(file -> !file.trim().equalsIgnoreCase(url.trim()));
        if (files.isEmpty()) product.setFiles(null);
        else product.setFiles(files);

        this.productRepository.save(product);
    }

    public boolean validateProductWithUser(Long productId, User user){
        return this.productUserService.validateExistProductAndUser(this.findById(productId), user);
    }

    public void createRelationWithUser(List<Long> users, Long productId){
        users.forEach(user -> {
            Product product = findById(productId);
            if (!this.validateProductWithUser(productId, this.userService.getUserById(user))) {
                this.productUserService.createProductUser(product, this.userService.getUserById(user));
            }
        });
    }
}
