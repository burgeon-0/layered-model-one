package org.burgeon.sbd.controller;

import org.burgeon.sbd.controller.req.PageQuery;
import org.burgeon.sbd.controller.req.post.PostCmd;
import org.burgeon.sbd.controller.res.MultiResponse;
import org.burgeon.sbd.controller.res.Response;
import org.burgeon.sbd.controller.res.SingleResponse;
import org.burgeon.sbd.controller.res.post.PostVO;
import org.burgeon.sbd.infra.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Sam Lu
 * @date 2021/5/28
 */
@RestController
@RequestMapping(Constants.API_CLIENT)
public class PostController {

    @PostMapping("/posts")
    @ResponseStatus(HttpStatus.CREATED)
    public SingleResponse<String> createPost(@Valid @RequestBody PostCmd postCmd) {
        // TODO create post
        return SingleResponse.ok("");
    }

    @PutMapping("/posts/{id}")
    public Response updatePost(@PathVariable("id") String id, @Valid @RequestBody PostCmd postCmd) {
        // TODO update post
        return Response.ok();
    }

    @DeleteMapping("/posts/{id}")
    public Response deletePost(@PathVariable("id") String id) {
        // TODO delete post
        return Response.ok();
    }

    @GetMapping("/posts")
    public MultiResponse<PostVO> listPost(@Valid @ModelAttribute PageQuery pageQuery) {
        // TODO list post
        return MultiResponse.ok(null);
    }

    @GetMapping("/posts/{id}")
    public SingleResponse<PostVO> getPost(@PathVariable("id") String id) {
        // TODO get post
        return SingleResponse.ok(null);
    }

}
