package com.example.rules;

import com.example.rules.grammar.IRuleSet;
import com.example.rules.grammar.RuleSet;
import com.example.rules.grammar.specification.CompositeSpecification;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class RuleSetTest {

    CompositeSpecification ruleReturnsTrue = mock(CompositeSpecification.class);
    CompositeSpecification ruleReturnsFalse = mock(CompositeSpecification.class);
    CompositeSpecification ruleAlsoReturnsTrue = mock(CompositeSpecification.class);

    @Before
    public void setup() {
        when(ruleReturnsTrue.isSatisfiedBy(anyString())).thenReturn(true);
        when(ruleReturnsFalse.isSatisfiedBy(anyString())).thenReturn(false);
        when(ruleAlsoReturnsTrue.isSatisfiedBy(anyString())).thenReturn(true);
    }

    @Test
    public void isSatisfiedBy_true() {
        IRuleSet ruleSet = new RuleSet();
        ruleSet.addRule(ruleReturnsTrue);
        ruleSet.addRule(ruleAlsoReturnsTrue);

        assertTrue(ruleSet.isSatisfiedBy("json"));
    }

    @Test
    public void isSatisfiedBy_false() {
        IRuleSet ruleSet = new RuleSet();
        ruleSet.addRule(ruleReturnsTrue);
        ruleSet.addRule(ruleReturnsFalse);

        assertFalse(ruleSet.isSatisfiedBy("json"));
    }
}
