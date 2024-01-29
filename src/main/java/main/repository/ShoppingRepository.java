package main.repository;

import lombok.RequiredArgsConstructor;
import main.dto.ItemDto;
import main.model.Item;
import main.repository.method.ShoppingRepoMethod;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@RequiredArgsConstructor
public class ShoppingRepository implements ShoppingRepoMethod {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Item getItem(int itemId) {
        return jdbcTemplate.queryForObject("select * from Items where itemId=?", new Object[]{itemId},
                new BeanPropertyRowMapper<>(Item.class));
    }

    @Override
    public List<Item> getRecentItems() {
        return jdbcTemplate.query("select * from Items order by createdTime desc limit 3", new BeanPropertyRowMapper<>(Item.class));
    }

    @Override
    public List<Item> getCheapestItems() {
        return jdbcTemplate.query("select * from Items order by price asc", new BeanPropertyRowMapper<>(Item.class));
    }

    @Override
    public List<Item> getExpensiveItems() {
        return jdbcTemplate.query("select * from Items order by price desc", new BeanPropertyRowMapper<>(Item.class));
    }

    @Override
    public List<Item> getFamousItems() {
        return jdbcTemplate.query("select * from Items order by hits desc", new BeanPropertyRowMapper<>(Item.class));
    }

    @Override
    public List<Item> getItemsByName(String name) {
        return jdbcTemplate.query("select * from Items where itemName like ?", new Object[]{"%" +name+ "%"},
                new BeanPropertyRowMapper<>(Item.class));
    }

    @Override
    public List<Item> getItemsByDetail(String detail) {
        return jdbcTemplate.query("select * from Items where detail like ?", new Object[]{"%"+detail+"%"},
                new BeanPropertyRowMapper<>(Item.class));
    }

    @Override
    public List<Item> getItemsByAsc() {
        return jdbcTemplate.query("select * from Items order by itemId asc", new BeanPropertyRowMapper<>(Item.class));
    }

    @Override
    public List<Item> getItemsByDesc() {
        return jdbcTemplate.query("select * from Items order by itemId desc", new BeanPropertyRowMapper<>(Item.class));
    }

    @Override
    public List<Item> getItemsByKeywordOrNull(String keyword) {
        return jdbcTemplate.query("select * from Items where itemName like ? or itemDetail like ?",
                new Object[]{"%"+ keyword +"%", "%"+ keyword +"%"}, new BeanPropertyRowMapper<>(Item.class));
    }

    @Override
    public void deleteItem(int itemId) {
        jdbcTemplate.update("delete from Items where itemId = ?", itemId);
    }

    @Override
    public void addItem(ItemDto itemDto) {
        String sql = "insert into Items (imgUrl, itemName, price, detail, hits, inventoryCount, soldCount) values (?,?,?,?,0,?,0)";
        jdbcTemplate.update(sql, itemDto.getImgUrl(), itemDto.getItemName(), itemDto.getPrice(), itemDto.getDetail(), itemDto.getInventoryCount());
    }
}
